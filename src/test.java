import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.WS4J;
import edu.cmu.lti.ws4j.impl.*;
import edu.cmu.lti.ws4j.util.PorterStemmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class test {
    private static ILexicalDatabase db = new NictWordNet();

    //RELATEDNESS CALCULATORS INCLUDED IN WS4J
    private static RelatednessCalculator[] rcs = { new HirstStOnge(db),
            new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
            new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) };

    //COMPUTATION OF 2 WORD SIMILARITY
    public static double compute(String word1,String word2){
        WS4JConfiguration.getInstance().setMFS(true);
        RelatednessCalculator rc = new Lin(db);
        List<POS[]> posPairs = rc.getPOSPairs();
        PorterStemmer a=new PorterStemmer();
        double maxScore = -1D;

        for(POS[] posPair: posPairs) {
            List<Concept> synsets1 = (List<Concept>)db.getAllConcepts(word1, posPair[0].toString());
            List<Concept> synsets2 = (List<Concept>)db.getAllConcepts(word2, posPair[1].toString());

            for(Concept synset1: synsets1) {
                for (Concept synset2: synsets2) {
                    Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2);
                    double score = relatedness.getScore();
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }
        }

        if (maxScore == -1D) {
            maxScore = 0.0;
        }
        return maxScore;
    }

    //cosine similarity computation
    public static double cosinesimilarity(Stack<Double> job,Stack<Double> user,double divider){
        double dotprod=0;
        double xsqsum=0;
        double ysqsum=0;
        while(!user.empty()){
            double holder=job.pop()/Math.sqrt(divider);//normalize the job qualification
            double holder2=user.pop();//user profile already normalized
            dotprod+=(holder*holder2);
            xsqsum+=(holder*holder);
            ysqsum+=(holder2*holder2);
        }
        if(dotprod==0){
            return 0;
        }
        return (dotprod/(Math.sqrt(xsqsum)*Math.sqrt(ysqsum)));
    }

    //job computation if user and job qualification equal push 1 else push 0
    //creates a vector for the job and user
    public static void jobcompute(id user, LinkedList<id> jobs,Stack<Double> userprof,boolean WuPalmer){
        BStreeint tree=new BStreeint();
        while(!jobs.isEmpty()){
            Stack<Double> vector1 = new Stack<>();
            Stack<Double> vector2 = (Stack<Double>) userprof.clone();//transfer user profile values
            id joboffering=jobs.pop();
            double divider=0;
            if(user.graduate.equals(joboffering.graduate)){
                vector1.push(1.0);
                divider++;
            }else{
                vector1.push(0.0);
            }
            if(user.workexperience>=joboffering.workexperience){
                vector1.push(1.0);
                divider++;
            }else{
                vector1.push(0.0);
            }
            if(user.field.equals(joboffering.field)){
                vector1.push(1.0);
                divider++;
            }else{
                if(WuPalmer){
                    vector1.push(compute(user.field,joboffering.field));
                }else{
                    vector1.push(0.0);
                }
            }

            if(user.pay<=joboffering.pay){
                vector1.push(1.0);
                divider++;
            }else{
                vector1.push(0.0);
            }

            if(divider==0){
                divider=1;
            }
            tree.add(cosinesimilarity(vector1,vector2,divider)*100,joboffering);//add value of user profile to tree for sorting
        }
        tree.printinorder();//print tree once all job listing similarity has been computed
    }

    //file to string for ease of conversion
    public static String filetoString(File sched) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(sched));
        String program="";
        String line=reader.readLine();
        while (line != null)
        {
            program = program + line + System.lineSeparator();
            line = reader.readLine();
        }

        return program;
    }

    //user profile creation
    public static Stack<Double> userprofile()throws IOException{
        Scanner sc=new Scanner(System.in);
        Stack<Double> vals=new Stack<>();
        String ftos=filetoString(new File("getval.txt"));
        String[] tableString=ftos.split("\n"); //convert table value to string
        LinkedList<Double> dividers= new LinkedList<>(); //dividing value to normalize data
        LinkedList<double[]> rawvals=new LinkedList<>(); //values of each row
        LinkedList<Double> likability=new LinkedList<>(); //ordered list of likeability rating

        String[] row;
        double[] valhold;
        while(true){
            torate();
            System.out.print("INPUT NUM OF LIKED JOB OFFER: ");
            int a=sc.nextInt();
            if(a==-1){
                break;
            }else{
                double iterator=0;
                row=tableString[a-1].split(" ");
                valhold=new double[4];
                for(int i=0;i<row.length;i++){
                    double value =Double.parseDouble(row[i]);
                    if(value==1){
                        iterator++;
                    }
                    valhold[i]=value;
                }
                rawvals.add(valhold);
                likability.addLast(1.0);
                dividers.addLast(iterator);
            }
        }

        while(true){
            torate();
            System.out.print("INPUT NUM OF DISLIKED JOB OFFER: ");
            int a=sc.nextInt();
            if(a==-1){
                break;
            }else{
                double iterator=0;
                row=tableString[a-1].split(" ");
                valhold=new double[4];
                for(int i=0;i<row.length;i++){
                    double value =Double.parseDouble(row[i]);
                    if(value==1){
                        iterator++;
                    }
                    valhold[i]=value;
                }
                rawvals.add(valhold);
                likability.addLast(-1.0);
                dividers.addLast(iterator);
            }
        }

        //likability size is equal to total number of columns
        for(int i=0;i<likability.size();i++){
            LinkedList<Double> hold=new LinkedList<>();
            double dotprod=0;
            int iter=rawvals.size();

            //normalize value then get all values in a column
            for(int j=0;j<iter;j++){
                double divideby=dividers.pop();
                double[] values=rawvals.pop();
                hold.addLast(values[i]/Math.sqrt(divideby));
                rawvals.addLast(values);
                dividers.addLast(divideby);
            }

            //column is then multiplied and summed to the user rating
            while(!hold.isEmpty()){
                double holder=hold.pop();
                double holder2=likability.pop();
                dotprod+=(holder*holder2);
                likability.addLast(holder2);
            }

            //final value pushed to a stack
            vals.push(dotprod);
        }
        return vals;
    }

    public static void torate() throws IOException {
        System.out.println(filetoString(new File("choose.txt")));
    }

    public static void main(String[] args) throws IOException {
        id user=new id("Diploma",3,"business",50000);

        Stack<Double> userprofile=userprofile();
        String holder=filetoString(new File("data.txt"));
        String[] holder2=holder.split("\n");
        LinkedList<id> joboffers=new LinkedList<>();

        //places split string to id depending on category
        for(int i=0;i<holder2.length;i++){
            String[] holder3=holder2[i].split(" ",4);
            id jobs=new id(holder3[3].trim(),Integer.parseInt(holder3[2].trim()),holder3[0].trim(),Integer.parseInt(holder3[1].trim()));
            joboffers.add(jobs);
        }

        jobcompute(user,joboffers,userprofile,true);
    }

}
