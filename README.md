# gdx-dialogs
libGDX extension providing cross-platform support for native dialogs

![Alt text](/assets/dialogs.jpg?raw=true "Examples")

## Updates & News
Follow me to receive release updates about this and my other projects (Promise: No BS posts)

https://twitter.com/TomGrillGames and https://www.facebook.com/tomgrillgames

I will also stream sometimes when developing at https://www.twitch.tv/tomgrill and write a blog article from time to time at http://tomgrill.de 

## Version

Current stable: **1.3.0**
(Snapshot versions are no longer provided)

##Supported Platforms
Android, iOS, iOS-MOE, Desktop (kinda)
Desktop show Java UI Dialogs which usually dont fit very well in desktop game. I added Desktop support mainly to make testing while developing easier.

##Requirements
libGDX 1.9.3+, Android API 9+, ios 6+, ios-moe 9+

##Currently available dialog types
1. ButtonDialog: shows a dialog with 1-3 buttons for the user to click.
2. ProgressDialog: Shows dialog with a progress wheel. Not cancelable by the user.
3. TextPrompt: On screen display with text input.

## Installation
**Core**

Add this to your build.gradle core dependencies
```gradle
compile "de.tomgrill.gdxdialogs:gdx-dialogs-core:1.3.0"
```

**Android**

Add this to your build.gradle android dependencies
```gradle
compile "de.tomgrill.gdxdialogs:gdx-dialogs-android:1.3.0"
```

Copy the [/gdx-dialogs-android/res](/gdx-dialogs-android/res) folder from this project to your android project. Keep the directory structure.

You can edit the [/gdx-dialogs-android/res/values-v11/styles.xml](/gdx-dialogs-android/res/values-v11/styles.xml) if you want use another theme like Holo.Light

If your project already has styles.xml file make sure you merge what you need from both files.

**iOS**

Add this to your robovm.xml
```xml
<forceLinkClasses>
    ....
    <pattern>de.tomgrill.gdxdialogs.ios.IOSGDXDialogs</pattern>
</forceLinkClasses>
```

Add this to your build.gradle ios dependencies
```gradle
compile "de.tomgrill.gdxdialogs:gdx-dialogs-ios:1.3.0"
```

**iOS-MOE**

Add this to your build.gradle ios-moe dependencies
```gradle
compile "de.tomgrill.gdxdialogs:gdx-dialogs-ios-moe:1.3.0"
```

**Desktop**

Add this to your build.gradle desktop dependencies
```gradle
compile "de.tomgrill.gdxdialogs:gdx-dialogs-desktop:1.3.0"
```

**HTML(GWT)**

Add this to your build.gradle html dependencies
```gradle
compile "de.tomgrill.gdxdialogs:gdx-dialogs-html:1.3.0"
compile "de.tomgrill.gdxdialogs:gdx-dialogs-core:1.3.0:sources"
compile "de.tomgrill.gdxdialogs:gdx-dialogs-html:1.3.0:sources"
```

Add this to your GdxDefinition.gwt.xml
```xml
<inherits name='de.tomgrill.gdxdialogs.html.gdx_dialogs_html' />
<inherits name="de.tomgrill.gdxdialogs.core.gdx_dialogs_core" />
```


## Usage

**View the gdx-dialogs sample app**
https://github.com/TomGrill/gdx-dialogs-app

**Enable**

```
GDXDialogs dialogs = GDXDialogsSystem.install();
```

**ButtonDialog**

```java
GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
bDialog.setTitle("Buy a item");
bDialog.setMessage("Do you want to buy the mozarella?");

bDialog.setClickListener(new ButtonClickListener() {

	@Override
	public void click(int button) {
		// handle button click here
	}
});

bDialog.addButton("No");
bDialog.addButton("Never");
bDialog.addButton("Yes, nomnom!");

bDialog.build().show();
```

**ProgressDialog**
```java
GDXProgressDialog progressDialog = dialogs.newDialog(GDXProgressDialog.class);

progressDialog.setTitle("Download");
progressDialog.setMessage("Loading new level from server...");

progressDialog.build().show();
```

**TextPrompt**
```java
GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);

textPrompt.setTitle("Your name");
textPrompt.setMessage("Please tell me your name.");

textPrompt.setCancelButtonLabel("Cancel");
textPrompt.setConfirmButtonLabel("Save name");

textPrompt.setTextPromptListener(new TextPromptListener() {

	@Override
	public void confirm(String text) {
	  // do something with the user input
	}

	@Override
	public void cancel() {
	  // handle input cancel
	}
});

textPrompt.build().show();
```

**Open GL Context**

Sometimes you want to call a method within one of your listeners which requires OpenGL context from the libgdx main thread. Since all dialogs run in their own thread (without OpenGL context) you have to make sure your method runs in the main thread by wrapping as postRunnable:

```java
dialog.setClickListener(new ButtonClickListener() {
    @Override
    public void click(int button) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                // call the a method which requires OpenGL context.
            }
        });
    }
});
```

## Create your own dialogs.

In case you require are certain dialog (F.e. DatePicker, ProgressBar, ....) which is not supported by gdx-dialogs yet you can write your own dialog.

1. Create a interface like this (in core project): [GDXButtonDialog](gdx-dialogs-core/src/de/tomgrill/gdxdialogs/core/dialogs/GDXButtonDialog.java)
2. Create the implementation for Android like this (in android project): [AndroidGDXButtonDialog](gdx-dialogs-android/src/de/tomgrill/gdxdialogs/android/dialogs/AndroidGDXButtonDialog.java)
3. Create the implementation for Desktop like this (in desktop project): [DesktopGDXButtonDialog](gdx-dialogs-desktop/src/de/tomgrill/gdxdialogs/desktop/dialogs/DesktopGDXButtonDialog.java)
4. Create the implementation for iOS like this (in ios project): [IOSGDXButtonDialog](gdx-dialogs-ios/src/de/tomgrill/gdxdialogs/ios/dialogs/IOSGDXButtonDialog.java)
5. Create a fallback implementation with empty methods (in core project): [FallbackGDXButtonDialog](gdx-dialogs-core/src/de/tomgrill/gdxdialogs/core/dialogs/FallbackGDXButtonDialog.java)

If your dialog is written register it like this:

```java
if(Gdx.app.getType() == ApplicationType.Android) {
	dialogs.registerDialog("package.for.your.dialog.interface.GDXButtonDialog", "package.for.your.dialog.os.specific.implementation.AndroidGDXButtonDialog");
}

else if(Gdx.app.getType() == ApplicationType.Desktop) {
	dialogs.registerDialog("package.for.your.dialog.interface.GDXButtonDialog", "package.for.your.dialog.os.specific.implementation.DesktopGDXButtonDialog");
}

else if(Gdx.app.getType() == ApplicationType.iOS) {
	dialogs.registerDialog("package.for.your.dialog.interface.GDXButtonDialog", "package.for.your.dialog.os.specific.implementation.IOSGDXButtonDialog");
}

else {
	dialogs.registerDialog("package.for.your.dialog.interface.GDXButtonDialog", "package.for.your.dialog.os.specific.implementation.FallbackGDXButtonDialog");
}
```

Use your own dialog:

```java
dialogs.newDialog(GDXButtonDialog.class); // Use your dialog interface here
```

**Note:** Every platform specific implementation must have constructor even if it is empty. Android implementations must have a contructor accepting Activity parameter.

**Increase your karma points :) Share your dialog with us, add it to this repository and make a Pull Request.**

##Release History

Release history for major milestones (available via Maven):

* Version 1.3.0: Bugfix: Submodules had dependency on SNAPSHOT versions
* Version 1.2.5: Bugfix: DesktopTextPrompt;
* Version 1.2.4: Bugfix: DesktopTextPrompt;
* Version 1.2.3: Bugfix: DesktopTextPrompt not showing default value;
* Version 1.2.2: TextPrompt can now be used as a password field. Use setInputType(...);
* Version 1.2.1: TextPrompt dialogs have new setMaxLength() method.
* Version 1.2.0: gwt/html support
* Version 1.1.0: ios-moe support, ios support, Android proguard setup now out of the box
* Version 1.0.0: no changes, just released 0.2.0 as first stable build
* Version 0.2.0: API Changes. You can create own dialogs now.
* Version 0.1.0: Initial Release

##Reporting Issues

Something not working quite as expected? Do you need a feature that has not been implemented yet? Check the issue tracker and add a new one if your problem is not already listed. Please try to provide a detailed description of your problem, including the steps to reproduce it.

##Contributing

Awesome! If you would like to contribute with a new feature or a bugfix, fork this repo and submit a pull request.

##License

The gdx-dialogs project is licensed under the Apache 2 License, meaning you can use it free of charge, without strings attached in commercial and non-commercial projects. We love to get (non-mandatory) credit in case you release a game or app using gdx-dialogs!

### Apps that integrate with gdx-dialogs
Please send a pull request to add your apps to this list.
* [Minesweeper](https://play.google.com/store/apps/details?id=com.popoko.minesweeper)
* [Chess](https://play.google.com/store/apps/details?id=com.popoko.chessru)
* [Gomoku](https://play.google.com/store/apps/details?id=com.popoko.gomokukr)
* [Tic Tac Toe ⑤](https://play.google.com/store/apps/details?id=com.popoko.gomokuvn) 
* [Tic Tac Toe ⑤ for iOS](https://apps.apple.com/us/app/id1107349135)

