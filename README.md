
## Mobile Development Documentation


### Features
  * **Personal Calorie Tracking**. Track daily calorie intake to achieve and maintain your desired body weight.
    
  * **Food Calorie Information**. Access detailed calorie information for various foods to help achieve your weight goals.
    
  * **Food Image Calorie Prediction**. Predict the amount of calories in your food using AI image recognition.
    
  * **Personal Weight Tracking**. Monitor and log your daily weight to ensure you stay on track with your body weight goals.
    
  * **Personalized Workout Plan**. Create custom workout plans based on your preferences, including difficulty level, targeted muscles, and available equipment.
    
  * **Recommended Workout Plan**. Receive workout recommendations based on your current workout selections.
    
  * **Pick Image From Gallery**. Select images from your gallery to upload and analyze.
    
  * **Take Image From Camera**. Capture images directly from your camera for immediate analysis.
    
  * **Send Image to Server**. Upload the food images to our server to predict the calorie content of the food.


#### Dependencies :
  - [Lifecycle & LiveData](https://developer.android.com/jetpack/androidx/releases/lifecycle)
  - [Glide](https://github.com/bumptech/glide)
  - [Circleimageview](https://github.com/hdodenhof/CircleImageView)
  - [Android Material](https://github.com/material-components/material-components-android/tree/master/docs/components)
  - [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
  - [Okhttp3](https://square.github.io/okhttp/)
  - [Retrofit](https://square.github.io/retrofit/)
  - [Datastore-preferences](https://developer.android.com/topic/libraries/architecture/datastore)
  - [Justifytext](https://github.com/ufo22940268/android-justifiedtextview/blob/master/README.md)
  - [Picasso](https://square.github.io/picasso/)
  - [Ucrop](https://github.com/Yalantis/uCrop)

 
## Getting Started Application

- ### Prerequisites
    - ##### Tools Sofware
      1. Android Studio Jellyfish | 2023.3.1, 3.2-8.4 or latest [Android Studio](https://developer.android.com/studio)
      2. JRE (Java Runtime Environment) or JDK (Java Development Kit)

- ### Installation
    1. Contact the developer to get the Base URL for the application
    2. Clone this repositiory and import into Android Studio
       ```
         https://github.com/FitFirst/android-bangkit-capstone.git
       ```
    3. Enter the Base URL in buildConfigField `build.graddle`
       ```
       buildTypes {
           named("release") {
             buildConfigField("String", "BASE_URL", "The Base URL")
           }
       
          named("debug") {
            buildConfigField("String", "BASE_URL", "The Base URL")
          }
       }
       ```
