
<h1 align="center">Content Based Recommendation</h3>
 
 - This job suggestion algorithm aims to provide people with a close approximation as to what job offering would be most suitable for them based on their job qualifications and what they are looking for in a job



 
<details open="open">
  <h2>Table of Contents</h2>
  <ol>
    <li>
      <a href="#intro">Introduction</a>
    </li>
    <li>
      <a href="#">Methodology</a>
    </li>
      <li>
      <a href="#setup">Concepts</a>
    </li>
    <li><a href="#revisions">Results</a></li>
  </ol>
</details>

<h2 id="intro">Introduction</h2>

Content based recommendation was used because this approach recommends the user the appropriate items through the user’s preferences, a user profile is made weighing the likes and dislikes of each attribute of an item and if they are similar it is recommended

<h2 id="reqs">Methodology</h2>


Content based recommendation systems can be done in two ways. The first is through an unweighted method, this method does not utilize user ratings. Instead, this method checks the preference of the user through the user’s activities. For example, the user bought multiple items in a store, the program checks the individual attributes (color, shape, functionality) of the objects and assigns a 1 value to each attribute that was present and 0 to each attribute that was not present. The second is the weighted method that uses the user’s rating on multiple items (likes and dislikes) and creates a user profile that can be used to predict the user’s likability for items he has yet to see. For example, if the user watched 3 out of 10 movies the program would check whether he liked or disliked each of the three movies and through that assigns a value to each attribute (genre, actors, etc.) and makes a user profile from that and compares the rest
of the unseen movies with this user profile and checks whether the user might like it or not. This can still make use of Boolean values; the only difference is that the values of each item contain a weight. The research will check both methods and see which is better in terms of accuracy.



<h2 id="setup">Concepts</h2>

<h3 id="setup" align="center">Unweighted Recommendation</h2>

The hand computations were done using excel as there are only 2 combinations and 4 attributes that was taken; thus, there are only 16 possibilities a 1 would represent the job offer as having an equal value to what the user  wants and 0 to be not the same as what the user wants

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/1.png"> 
  </p>
    <p align="center">
Figure 1
  </p>
Because the value of each attribute was not weighted the values that would be gotten from using the unweighted recommendation were very similar with each other

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/2.png"> 
  </p>
    <p align="center">
Figure 2
  </p>
Using this method of recommendation, we can see that it is as if the similarity is directly proportional to the number of attributes or qualifications present in the job offer, we can more clearly see this after plotting the Similarity vs Attribute chart
<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/3.png"> 
  </p>
  <p align="center">
Graph 1 
  </p>
The values in the similarity were taken from the use of cosine similarity through which the first vector would always be the job offer 1 and the second vector would be any of the other vectors as we assume job offer 1 to be the ideal job offer that the user can get. The values of the attribute for each job offer were normalized using the number of attributes each job offer had, thus they were not 1 and 0 for the sake of computation. As we can see in Graph 1 as the attributes of the job offer increases, by increases this means that there are more and more attributes that are in-line with the user’s qualification, the similarity of the job offer would increase, this does not seem to be a problem at first; however, this proves to be an issue when it comes to the accuracy of the recommendation. As we can see in job offer no3 and job offer no9 of Figure 2, although job offer no 3 is not in the correct field of the user, the program detects it to be of higher importance than job offer no9 in which it is in the correct field. Because we do not know which values are important for our user, the program treats all values to be of equal importance with one another, thus producing the effect seen in Graph 1 where the more attributes that match the user, the higher the similarity value the job offer will get.

<h3 id="setup" align="center">Weighted Recommendation</h2>

For the weighted recommendations, because we do not have a user to be surveyed for which attributes matter to them the most, an example of of 3 likes and 1 dislike was made. The three dislikes correspond to job offer 1,8, and 9. Likes are represented as a 1 and dislikes are represented as a -1.

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/4.png"> 
  </p>
  <p align="center">
Figure  3
 </p>

Through this assumption we can calculate the user profile using the sum of the products of each attribute to the user rating. The empty spots in the user rating means that the user has not yet seen those job offerings thus it has a value of 0. We then get a user profile

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/12.png"> 
  </p>
  <p align="center">
Figure  4
 </p>
 
 From the user profile alone, we are able to see the importance values that were gotten from the user ratings. The highest importance in this case is the field of the user followed by the graduate status, and whether the pay is high and followed by work experience. These values depend on the user ratings as such if the ratings were to be different, we would get a new user profile.
 
<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/5.png"> 
  </p>
  <p align="center">
Figure  5
 </p>

Figure 5 can represent a fresh graduate that does needs the high pay but has no work experience as such the job offers, he liked would have been ones that had a higher or equal pay to the ones present in his info and offers for graduates and in his field were important but not as important as the pay. The dislike of the work experience also makes the attribute negative for the user. We can now see what the computed similarity would be from this profile.

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/6.png"> 
  </p>
  <p align="center">
Figure  6
 </p>




<h2 id="revisions">Results</h2>
Based on the hand computation presented above, we now know which type of content-based recommendation system to use. For consistency, the user profile used in the application was that of the same in Figure 4. The program took in 50 job offerings from a text documenting and stores it inside of a linked list. After which it would compute for the similarity percentage and places it inside of a binary search tree so that it will always be in order. The query was that the user has a diploma in the field of business with 3 years of experience and looking for a job that pays 50,000. As we can see in the results in Figure 7 the job offerings that are in the field of business was the highest result with a similarity rating of 73.37%. Followed by another business job with a 54.57% due to it not having the desired pay that the user wants. These results are in line with Job offer 1 and 2 in Figure 6 due to them representing the data that has all the attributes the user wants and the data in which almost all values are what the user wants except for the pay. We can also notice that the values are slightly not the same as the ones in the Figure 6 as the user profile for the application had its values rounded off to the hundredths place.

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/9.png"> 
  </p>
  <p align="center">
Figure  7
 </p>

As seen in Figure 7 is an issue that is prevalent with the direct use of equals() for applications like these where recommendations are the main feature of the program. We can see that with the term “business” it is a relatively broad topic that should span terms such as “finance” or even “marketing” but with the direct use of equals() we can see that marketing is at 0% and finance is only at 52% which is wrong as it is passed by job offerings for the field of programming and developer. This is where the use of NLP is crucial as we can give a value to these words instead of directly giving them a value of 0. We can see in figure 13 that using the WuPalmer wordnet similarity in WS4J we are able to check for the relatedness between two words.

<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/10.png"> 
  </p>
  <p align="center">
Figure  8
 </p>
As seen on Figure 8 using the WuPalmer wordnet similarity from WS4J allows broader term results rather than merely inputting 0 if the two strings do not match. Although this method is not perfect as seen for the results in auditor and sales, it is good enough such that other terms relating to business such as finance, marketing, service, and accounting get a high value through this method.
<p align="center">
   <img width="400" src="https://raw.githubusercontent.com/tyrone890123/Content-Based-Recommendation/main/assets/11.png"> 
  </p>
  <p align="center">
Figure  9
 </p>
We can already see the effects of this in the values on Figure 9 as finance, marketing, and even accounting is present in the top searches. Although this method is not perfect, it allows for more possible recommendations that the user might like and could
possibly increase the accuracy of the program. As such this is concluded to be the preferred method in recommending job offerings to users

