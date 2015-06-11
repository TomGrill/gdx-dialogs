# gdx-dialogs
libGDX extension providing cross-platform support for native dialogs

![Alt text](/assets/dialogs.jpg?raw=true "Examples")

## Version
Current status i **beta**. (It is not recommended to use this library in production releases.)

Current snapshot: **0.1.0**

Current stable: **not yet existing**

##Supported Platforms
Android, iOS, Desktop (kinda)
Desktop show Java UI Dialogs which usually dont fit very well in desktop game. I added Desktop support mainly to make testing while developing easier.

##Currently available dialog types
1. ButtonDialog: shows a dialog with 1-3 buttons for the user to click.
2. ProgressDialog: Shows dialog with a progresswheel. Not cancelable by the user.
3. TextPrompt: On screen display with text input.

## Installation
**Core**

Add this to your libGDX build.gradle
```
project(":core") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxdialogs:gdx-dialogs-core:0.1.0-SNAPSHOT"
	    ...
	}
}
```

**Android**

Add this to your libGDX build.gradle
```
project(":android") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxdialogs:gdx-dialogs-android:0.1.0-SNAPSHOT"
	    ...
	}
}
```

Copy the the [/gdx-dialogs-android/res](/gdx-dialogs-android/res) folder from this project to your android project. Keep the directory structure.

You can edit the [/gdx-dialogs-android/res/values-v11/styles.xml](/gdx-dialogs-android/res/values-v11/styles.xml) if you want use another theme like Holo.Light

**iOS**
Add this to your robovm.xml
```
<forceLinkClasses>
    ....
    <pattern>de.tomgrill.gdxdialogs.ios.IOSDialogManager</pattern>
</forceLinkClasses>
```

Add this to your libGDX build.gradle
```
project(":ios") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxdialogs:gdx-dialogs-ios:0.1.0-SNAPSHOT"
	    ...
	}
}
```

**Core**

Add this to your libGDX build.gradle
```
project(":desktop") {
	dependencies {
	    ...
	    compile "de.tomgrill.gdxdialogs:gdx-dialogs-desktop:0.1.0-SNAPSHOT"
	    ...
	}
}
```

## Usage

**View the gdx-dialogs sample app**
https://github.com/TomGrill/gdx-dialogs-app

**Enable**
```
DialogSystem dSystem = new DialogSystem(); // You may only do this once.
DialogManager	dManager = dSystem.getDialogManager();
```

**ButtonDialog**
```
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

**ButtonDialog**
```
    ProgressDialog progressDialog = dManager.newProgressDialog();

		progressDialog.setTitle("Download");
		progressDialog.setMessage("Loading new level from server...");

		progressDialog.build().show();
```

**TextPrompt**
```
    TextPrompt textPrompt = dManager.newTextPrompt();

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

##Release History

Release history for major milestones (available via Maven):

*Version 0.1.0: Initial Release

##Reporting Issues

Something not working quite as expected? Do you need a feature that has not been implemented yet? Check the issue tracker and add a new one if your problem is not already listed. Please try to provide a detailed description of your problem, including the steps to reproduce it.

##Contributing

Awesome! If you would like to contribute with a new feature or a bugfix, fork this repo and submit a pull request.

##License

The gdx-dialogs project is licensed under the Apache 2 License, meaning you can use it free of charge, without strings attached in commercial and non-commercial projects. We love to get (non-mandatory) credit in case you release a game or app using gdx-dialogs!
