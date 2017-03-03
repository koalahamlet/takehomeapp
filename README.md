# takehomeapp
This app was made as a take home assignment, to demonstrate a simple application that continues to display data 
even when the user has slow to no signal, after at least one startup. 

This project should function well, right out of the box. Simply import into AndroidStudio as a gradle project.

I tried to separate the app into logical components of Persistence, DataBaseServices, Networking, Models, Utils, and Injection.
I've also attempted to provide a more thorough view of my architectural decisions in the commit messages.
So, make sure to read the expanded text. 


## libraries used
libraries used with a brief explanation as to why:

    compile 'com.android.support:appcompat-v7:25.1.0'  
    compile 'com.android.support:design:25.1.0'       
    compile 'com.android.support:cardview-v7:25.1.0'   
 I used these libraries because I wanted to use the wonderful components provided by the appcompat teams, while still providing an update UI to newer users. 
    
    compile 'com.squareup.retrofit2:retrofit:2.1.0'            
I used retrofit because I tend to prefer it for networking in android with clean REST apis. 

    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
I used Gson, because that's my favorite JSON converter. No other reason. 
    
    compile 'com.squareup.okhttp3:logging-interceptor:3.6.0'
This is so that I can get a nice log from Retrofit when something goes right/wrong with my networking and/or JSON parsing.

    apply plugin: 'realm-android'
I used realm to implement the majority of my persistence layer. This includes a fantastic api to be able to write to a single database
instance, and have access to those objects on my main thread.

    compile 'com.jakewharton:butterknife:7.0.1' 
I used butterknife because I enjoy its interface for binding views. "findViewById()", begone!

    compile 'com.github.bumptech.glide:glide:3.5.2'
I used Glide to handle image loading.
  
    compile 'com.jakewharton.timber:timber:4.1.0' 
Timber is for making my internal logging easier. Were this a production app, 
I would set up a release vs a debug tree as instructed in https://www.youtube.com/watch?v=0BEkVaPlU9A

    
