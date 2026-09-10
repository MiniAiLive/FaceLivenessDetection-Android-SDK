<div align="center">
   <h1> Face Liveness Detection Android SDK </h1>
   <img src=https://miniai.live/wp-content/uploads/2025/11/logo_text.png alt="MiniAiLive Logo"
   width="300">
</div>

## Welcome to the [MiniAiLive](https://www.miniai.live/)!
Upgrade your Android app with MiniAiLive's 3D Passive Face Liveness Detection!   With our advanced computer vision techniques, you can now enhance security and accuracy on your Android platform. Check out our latest repository containing a demonstration of 2D &amp; 3D passive face liveness detection (face anti-spoofing) capabilities.   Try it out today!


> **Note**
>
> SDK is fully on-premise, processing all happens on hosting server and no data leaves server.
> 
## Latest Face LivenessDetection SDK Download Link [Here](https://drive.google.com/drive/folders/1eSLvRYop4GXV4iz0E6nF1LauAfgpNGYi?usp=drive_link)

## Face LivenessDetection APK
<a href="https://play.google.com/store/apps/details?id=com.miniai.liveness&hl=en-HK" target="_blank">
  <img alt="" src="https://user-images.githubusercontent.com/125717930/230804673-17c99e7d-6a21-4a64-8b9e-a465142da148.png" height=80/>
</a>


## Liveness Test Results
<img src="https://github.com/MiniAiLive/MiniAI-Face-LivenessDetection-Android/assets/106538966/a5d1304a-d5f5-4af2-9ec6-a6f4e59674af_small.jpg" alt="Test4" width="150" />
<img src="https://github.com/MiniAiLive/MiniAI-Face-LivenessDetection-Android/assets/106538966/07a2c05b-2ed2-43e2-b58f-4799829b6ceb_small.jpg" alt="Test5" width="150" />
<img src="https://github.com/MiniAiLive/MiniAI-Face-LivenessDetection-Android/assets/106538966/7a2d1a01-e54e-4fd5-94e6-45adf1fbe5ed_small.jpg" alt="Test2" width="150" />
<img src="https://github.com/MiniAiLive/MiniAI-Face-LivenessDetection-Android/assets/106538966/8bada556-b6fe-4056-bb77-84f555a6b2eb_small.jpg" alt="Test3" width="150" />
<img src="https://github.com/MiniAiLive/MiniAI-Face-LivenessDetection-Android/assets/106538966/15957d97-8967-4950-a73d-30d66704e5bd_small.jpg" alt="Test1" width="150" />

<br></br>

https://github.com/MiniAiLive/MiniAI-Face-LivenessDetection-AndroidSDK/assets/153516004/99ae5b73-cb36-47d9-ae38-d91c031c2953

## Request license
Feel free to [Contact US](https://www.miniai.live/contact/) to get a trial License. We are 24/7 online on [WhatsApp](https://wa.me/+19162702374).

## SDK License

This project uses `MiniAiLive`'s liveness detection SDK. The SDK requires a license per `application ID`.

- The code below shows how to use the license:
```
var ret = FaceSDK.setActivation(
            "amDMEN2mo3JxYzYk5QWixuN5J45EpOYzPbUIzt+sMpw1zqbXPEE672e/r6RZFMsCsaqHb+vTi0UxuFmc" + 
                 "T/E78V2jjH0XAxRbI7GcIq3kg4rqz9YRZ5or9cwioSyT999iK02j1zCn4FEGqt6ufEGSqTsKEqGYMif2vMsqG" + 
                 "yN0GBNNk/yARzchJbElKkCRzkYlWRhThhK8BLPeZCQYPGif6ssQIM55/4EOTzwN4BHzyajYuzAOBBSedveC2I+" + 
                 "HyvSE9q+bpLmn+oMf0ZoUaMCmM6AUB2VXE1/uE0+IrhgWaXeCGqDJ6AOihm0QMqHdnf0Ks7qFcpg/zAYppeJlyd6tMw=="
        )
```
## About SDK

### Set up
1. Copy the SDK (`libfacesdk` folder) to the `root` folder in your project.

2. Add SDK to the project in `settings.gradle`.
```kotlin
include ':libfacesdk'
```

3. Add dependency to your `build.gradle`.
```kotlin
implementation project(path: ':libfacesdk')
```

### Initializing an SDK

- Step One

To begin, you need to activate the SDK using the license that you have received.
```kotlin
FaceSDK.setActivation("...")
```

If activation is successful, the return value will be `SDK_SUCCESS`. Otherwise, an error value will be returned.

- Step Two

After activation, call the SDK's initialization function.
```kotlin
FaceSDK.init(getAssets());
```
If initialization is successful, the return value will be `SDK_SUCCESS`. Otherwise, an error value will be returned.

### Face Detection and Liveness Detection

The `FaceSDK` offers a single function for detecting face and liveness detection, which can be used as follows:
```kotlin
FaceSDK.faceDetection(bitmap)
```

This function takes a single parameter, which is a `bitmap` object. The return value of the function is a list of `FaceBox` objects. Each FaceBox object contains the detected face rectangle, liveness score, and facial angles such as `yaw`, `roll`, and `pitch`.

### Yuv to Bitmap
The SDK provides a function called `yuv2Bitmap`, which converts a `yuv` frame to a `bitmap`. Since camera frames are typically in `yuv` format, this function is necessary to convert them to `bitmap`. The usage of this function is as follows:
```kotlin
Bitmap bitmap = FaceSDK.yuv2Bitmap(nv21, image.getWidth(), image.getHeight(), 7);
```
The first parameter is an `nv21` byte array containing the `yuv` data. 

The second parameter is the width of the `yuv` frame, and the third parameter is its height. 

The fourth parameter is the `conversion mode`, which is determined by the camera orientation.

To determine the appropriate `conversion mode`, the following method can be used:
```kotlin
 1        2       3      4         5            6           7          8

 888888  888888      88  88      8888888888  88                  88  8888888888
 88          88      88  88      88  88      88  88          88  88      88  88
 8888      8888    8888  8888    88          8888888888  8888888888          88
 88          88      88  88
 88          88  888888  888888
```

## Face & IDSDK Online Demo, Resources
<div style="display: flex; justify-content: center; align-items: center;"> 
   <table style="text-align: center;">
      <tr>
         <td style="text-align: center; vertical-align: middle;"><a href="https://github.com/MiniAiLive"><img src="https://miniai.live/wp-content/uploads/2024/10/new_git-1-300x67.png" style="height: 60px; margin-right: 5px;" title="GITHUB"/></a></td> 
         <td style="text-align: center; vertical-align: middle;"><a href="https://huggingface.co/MiniAiLive"><img src="https://miniai.live/wp-content/uploads/2024/10/new_hugging-1-300x67.png" style="height: 60px; margin-right: 5px;" title="HuggingFace"/></a></td> 
         <td style="text-align: center; vertical-align: middle;"><a href="https://demo.miniai.live"><img src="https://miniai.live/wp-content/uploads/2024/10/new_gradio-300x67.png" style="height: 60px; margin-right: 5px;" title="Gradio"/></a></td> 
      </tr> 
      <tr>
         <td style="text-align: center; vertical-align: middle;"><a href="https://docs.miniai.live/"><img src="https://miniai.live/wp-content/uploads/2024/10/a-300x70.png" style="height: 60px; margin-right: 5px;" title="Documentation"/></a></td> 
         <td style="text-align: center; vertical-align: middle;"><a href="https://www.youtube.com/@miniailive"><img src="https://miniai.live/wp-content/uploads/2024/10/Untitled-1-300x70.png" style="height: 60px; margin-right: 5px;" title="Youtube"/></a></td> 
         <td style="text-align: center; vertical-align: middle;"><a href="https://play.google.com/store/apps/dev?id=5831076207730531667"><img src="https://miniai.live/wp-content/uploads/2024/10/googleplay-300x62.png" style="height: 60px; margin-right: 5px;" title="Google Play"/></a></td>
      </tr>
   </table>
</div>

## Our Products

### Face Recognition SDK
| No | Project | Features |
|----|---------|-----------|
| 1 | [FaceRecognition-SDK-Docker](https://github.com/MiniAiLive/FaceRecognition-SDK-Docker) | 1:1 & 1:N Face Matching SDK |
| 2 | [FaceRecognition-SDK-Windows](https://github.com/MiniAiLive/FaceRecognition-SDK-Windows) | 1:1 & 1:N Face Matching SDK |
| 3 | [FaceRecognition-SDK-Linux](https://github.com/MiniAiLive/FaceRecognition-SDK-Linux) | 1:1 & 1:N Face Matching SDK |
| 4 | [FaceRecognition-LivenessDetection-SDK-Android](https://github.com/MiniAiLive/FaceRecognition-LivenessDetection-SDK-Android) | 1:1 & 1:N Face Matching, 2D & 3D Face Passive Liveness Detection SDK |
| 5 | [FaceRecognition-LivenessDetection-SDK-iOS](https://github.com/MiniAiLive/FaceRecognition-LivenessDetection-SDK-iOS) | 1:1 & 1:N Face Matching, 2D & 3D Face Passive Liveness Detection SDK |
| 6 | [FaceRecognition-LivenessDetection-SDK-CPP](https://github.com/MiniAiLive/FaceRecognition-LivenessDetection-SDK-CPP) | 1:1 & 1:N Face Matching, 2D & 3D Face Passive Liveness Detection SDK |
| 7 | [FaceMatching-SDK-Android](https://github.com/MiniAiLive/FaceMatching-SDK-Android) | 1:1 Face Matching SDK |
| 8 | [FaceAttributes-SDK-Android](https://github.com/MiniAiLive/FaceAttributes-SDK-Android) | Face Attributes, Age & Gender Estimation SDK |

### Face Liveness Detection SDK
| No | Project | Features |
|----|---------|-----------|
| 1 | [FaceLivenessDetection-SDK-Docker](https://github.com/MiniAiLive/FaceLivenessDetection-SDK-Docker) | 2D & 3D Face Passive Liveness Detection SDK |
| 2 | [FaceLivenessDetection-SDK-Windows](https://github.com/MiniAiLive/FaceLivenessDetection-SDK-Windows) | 2D & 3D Face Passive Liveness Detection SDK |
| 3 | [FaceLivenessDetection-SDK-Linux](https://github.com/MiniAiLive/FaceLivenessDetection-SDK-Linux) | 2D & 3D Face Passive Liveness Detection SDK |
| 4 | [FaceLivenessDetection-SDK-Android](https://github.com/MiniAiLive/FaceLivenessDetection-SDK-Android) | 2D & 3D Face Passive Liveness Detection SDK |
| 5 | [FaceLivenessDetection-SDK-iOS](https://github.com/MiniAiLive/FaceLivenessDetection-SDK-iOS) | 2D & 3D Face Passive Liveness Detection SDK |

### ID Document Recognition SDK
| No | Project | Features |
|----|---------|-----------|
| 1 | [ID-DocumentRecognition-SDK-Docker](https://github.com/MiniAiLive/ID-DocumentRecognition-SDK-Docker) | ID Document, Passport, Driver License, Credit Card, MRZ Recognition SDK |
| 2 | [ID-DocumentRecognition-SDK-Windows](https://github.com/MiniAiLive/ID-DocumentRecognition-SDK-Windows) | ID Document, Passport, Driver License, Credit Card, MRZ Recognition SDK |
| 3 | [ID-DocumentRecognition-SDK-Linux](https://github.com/MiniAiLive/ID-DocumentRecognition-SDK-Linux) | ID Document, Passport, Driver License, Credit Card, MRZ Recognition SDK |
| 4 | [ID-DocumentRecognition-SDK-Android](https://github.com/MiniAiLive/ID-DocumentRecognition-SDK-Android) | ID Document, Passport, Driver License, Credit Card, MRZ Recognition SDK |

### ID Document Liveness Detection SDK
| No | Project | Features |
|----|---------|-----------|
| 1 | [ID-DocumentLivenessDetection-SDK-Docker](https://github.com/MiniAiLive/ID-DocumentLivenessDetection-SDK-Docker) | ID Document Liveness Detection SDK |
| 2 | [ID-DocumentLivenessDetection-SDK-Windows](https://github.com/MiniAiLive/ID-DocumentLivenessDetection-SDK-Windows) | ID Document Liveness Detection SDK |
| 3 | [ID-DocumentLivenessDetection-SDK-Linux](https://github.com/MiniAiLive/ID-DocumentLivenessDetection-SDK-Linux) | ID Document Liveness Detection SDK |

### Web & Desktop Demo
| No | Project | Features |
|----|---------|-----------|
| 1 | [FaceRecognition-IDRecognition-Playground-Next.JS](https://github.com/MiniAiLive/FaceRecognition-IDRecognition-Playground-Next.JS) | FaceSDK & IDSDK Playground |
| 2 | [FaceCapture-LivenessDetection-Next.JS](https://github.com/MiniAiLive/FaceCapture-LivenessDetection-Next.JS) | Face Capture, Face LivenessDetection, Face Attributes |
| 3 | [FaceMatching-Windows-App](https://github.com/MiniAiLive/FaceMatching-Windows-App) | 1:1 Face Matching Windows Demo Application |

## About MiniAiLive
[MiniAiLive](https://www.miniai.live/) is a leading AI solutions company specializing in computer vision and machine learning technologies. We provide cutting-edge solutions for various industries, leveraging the power of AI to drive innovation and efficiency.

## Contact US
For any inquiries or questions, please contact us on [WhatsApp](https://wa.me/+19162702374).

<p align="center">
<a target="_blank" href="https://t.me/Contact_MiniAiLive"><img src="https://img.shields.io/badge/telegram-@MiniAiLive-blue.svg?logo=telegram" alt="www.miniai.live"></a>&emsp;
<a target="_blank" href="https://wa.me/+19162702374"><img src="https://img.shields.io/badge/whatsapp-MiniAiLive-blue.svg?logo=whatsapp" alt="www.miniai.live"></a>&emsp;
</p>
