
# MySpotify

* A dynamic music streaming application that enables authenticated users to browse, play, and save their favorite songs across genres, artists, and albums in real-time. Integrated with Deezer API to fetch music data seamlessly.
* Implemented secure user authentication using Firebase Authentication, and leveraged Firebase Firestore to persist user preferences, such as favorite songs and playlists.
* Optimized the app's architecture using Hilt and Dagger for dependency injection, improving code modularity and testing. Handled asynchronous data streams and state management using StateFlow, delivering a smooth and interactive user experience.
* Enhanced performance by utilizing Firebase Firestore with RecyclerView to automatically update and display the latest music  according to user activities, minimizing search and upload times.


## API Reference

#### Get all items

```http
  "https://deezerdevs-deezer.p.rapidapi.com/"
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `q` | `string` | **Artist Name/Album Name** |

#### Get item

```http
  "https://deezerdevs-deezer.p.rapidapi.com/search?q=eminem"
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### add(num1, num2)

Takes two numbers and returns the sum.


## Screenshots

![App Screenshot](https://drive.google.com/uc?export=view&id=1Mw-udW3tszNh7bCi4_24pKbGJwyZE2U4)

## Tech Stack

**Client:** XML, Kotlin, Material Design 3

**Server:** API, FirebaseFirestore

**Language:** Kotlin

**Architecture:** MVVM, Courotines & Flows

**Libraries:** Retrofit, Hilt & Dagger







## 🚀 About Me
I'm a Android application developer...and also ML enthusiast



## 🔗 Links
[![linkedin](www.linkedin.com/in/sri-prajwat)](https://www.linkedin.com/)


## 🛠 Skills
Kotlin, Python, PyTools, HTML, XML...

