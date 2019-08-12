# GoogleCustomSearch

Google custom search API is implemented in this Android API. 
Using OkHttp Library, API calls are made and using the Gson Library the json response is converted into Java Objects. 
The entire app is dependant on these Java Objects.

The user searches for anything on the first screen and the list of google search results from the API are displayed in the second screen.
The items in this list are expandable i.e.. on clicking any of the items, the view expands to give more information about the item.
The list is paginated i.e.. it loads only when user scrolls to the bottom of the list.
The list stops scrolling if there are no more images from the API.
On clicking any item in the list, the third activity comes up which gives more details about the item.

## Screenshots of the App :

<img src="https://github.com/Diksha65/GoogleCustomSearch/blob/master/app/src/main/java/com/assignment/googlecustomsearch/screenshots/Screen%201.jpg" width="250" height="500" />   <img src="https://github.com/Diksha65/GoogleCustomSearch/blob/master/app/src/main/java/com/assignment/googlecustomsearch/screenshots/Screen%202.jpg" width="250" height="500" />   <img src="https://github.com/Diksha65/GoogleCustomSearch/blob/master/app/src/main/java/com/assignment/googlecustomsearch/screenshots/Screen%202%20expandable%201.jpg" width="250" height="500" />

<img src="https://github.com/Diksha65/GoogleCustomSearch/blob/master/app/src/main/java/com/assignment/googlecustomsearch/screenshots/Screen%202%20expandable%202.jpg" width="250" height="500" />   <img src="https://github.com/Diksha65/GoogleCustomSearch/blob/master/app/src/main/java/com/assignment/googlecustomsearch/screenshots/Screen%203.jpg" width="250" height="500" />

## How to run this app :

To compile this app you need to have Android Studio installed on your laptop / desktop. And an Android phone running atleast Android 5.0(Lollipop).

To run the app :

1. Open Android Studio -> New project from version control
2. Clone the github repository into Andorid Studio
3. (Shift+F10) to run the code.

## Libraries used in this app :

1. OkHttp - to make an API call to the google custom search API.
2. PhotoView - to add the zooming functionality to the image in third screen
3. Glide - to load images into the imageView
4. Gson - to convert the JSON representation of API response to Java Objects
5. custom tabs - to implement the feature of chrome custom tabs and male transtition from android to web more seamlessly

## Challenges faced :

1. API had only 100 calls per day which was very posed challenges while developing the app.
2. There was no reliable documentation tyo understand the behaviour of the API so certain assumptions had to be taken.
3. The 'searchType' = 'Image' was not enabled on the api so we had to filter and apply checks while fetching the results.
4. The images returned by the Custom Search API sometimes do not correspond with Google Search and I felt that the results were not very accurate.
5. This was the first time I implemented Pagination so I found it difficult but it was a good challenge and I learnt a lot.

