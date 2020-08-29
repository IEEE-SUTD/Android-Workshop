# Java Android Crash Course

Author: Tan Li Yuan <br>
Intro to the most widely used 2D UI

### 1. Pre-Requisites<br>
#### Good to know
* Java Classes
* Day 1 Crash Course knowledge - Hope that you understood most of it!

### Activity Lifecycle
<img src="https://developer.android.com/guide/components/images/activity_lifecycle.png"
alt="Activity Lifecycle" title="Activity Lifecycle">

```onCreate()```
>Called when the activity is first created. This is where you should do all of your normal static set up: create views, 
>bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state, 
>if there was one. Always followed by onStart().

```onRestart()```
>Called after your activity has been stopped, prior to it being started again. Always followed by onStart()

```onStart()```
>Called when the activity is becoming visible to the user. Followed by onResume() if the activity comes to the foreground.

```onResume()```
>Called when the activity will start interacting with the user. At this point your activity is at the top of the activity
>stack, with user input going to it. Always followed by onPause().

```onPause()```
>Called as part of the activity lifecycle when an activity is going into the background, but has not (yet) been killed. 
>The counterpart to onResume(). When activity B is launched in front of activity A, this callback will be invoked on A. 
>B will not be created until A's onPause() returns, so be sure to not do anything lengthy here.

```onStop()```
>Called when you are no longer visible to the user. You will next receive either onRestart(), onDestroy(), or nothing, 
>depending on later user activity. Note that this method may never be called, in low memory situations where the system 
>does not have enough memory to keep your activity's process running after its onPause() method is called.

```onDestroy()```
>The final call you receive before your activity is destroyed. This can happen either because the activity is finishing 
>(someone called finish() on it, or because the system is temporarily destroying this instance of the activity to save 
>space. You can distinguish between> these two scenarios with the isFinishing() method.

Isn't this a mouthful? As a beginner to Android programming, this car analogy that you start off with:
```
protected void onCreate(...) {
    openGarageDoor();
    unlockCarAndGetIn();
    closeCarDoorAndPutOnSeatBelt();
    putKeyInIgnition();
}

protected void onStart() {
    startEngine();
    changeRadioStation();
    switchOnLightsIfNeeded();
    switchOnWipersIfNeeded();
}

protected void onResume() {
    applyFootbrake();
    releaseHandbrake();
    putCarInGear();
    drive();
}

protected void onPause() {
    putCarInNeutral();
    applyHandbrake();
}

protected void onStop() {
    switchEveryThingOff();
    turnOffEngine();
    removeSeatBeltAndGetOutOfCar();
    lockCar();
}

protected void onDestroy() {
    enterOfficeBuilding();
}

protected void onReachedGroceryStore(...) {
    Intent i = new Intent(ACTION_GET_GROCERIES, ...,  this, GroceryStoreActivity.class);
}

protected void onRestart() {
    unlockCarAndGetIn();
    closeDoorAndPutOnSeatBelt();
    putKeyInIgnition();
}
```

##### Why is the activity lifecycle an important concept?
Using the car concept, assuming that you drove a car, but you forgot to lock the car, or you drove off without releasing 
the handbrake.The outcome is irreparable damage to the car and/or your car gets stolen.

If you are writing an app, and you run data through your app, but at the end, you forget to stop running your data, your
app will face issues such as memory leaks, and run the risk of crashing your app. Another example is drawing a 3D object
onto your phone, but your forget to garbage collect your object, and this causes memory leakages, and cause your app to lag.

These are some simplified real life problems that software engineers face.

**Demo Time**

### Intent
What is an intent?
>a messaging object you can use to request an action from another app component

Intent is just a mode of communication between 2 layouts (Activities) <br>
In most apps, there are 2 common ways to change layouts, you can do it via activities, fragments. For today, I will be
demo-ing a simple activity layout change using buttons.

```
        secondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                //show how to pass information to another activity
                startIntent.putExtra("com.example.quicklauncher", "HELLO WORLD!");
                startActivity(startIntent);
            }
        });
```
In this code excerpt, the secondActivity btn initiates a startIntent to change the MainActivity layout to SecondActivity.
If you want to, you can send a string message using putExtra method. Lastly you have to call startActivity to initiate 
the change.

```
        if (getIntent().hasExtra("com.example.quicklauncher")) {
            TextView tv = (TextView)findViewById(R.id.textView);
            String text = getIntent().getExtras().getString("com.example.quicklauncher");
            tv.setText(text);
        }
```
This is the code from the SecondActivity.class, if you want to get the intent message from the getExtra method.

```
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String google = "http://www.google.com.sg";
                Uri webaddress = Uri.parse(google);

                Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webaddress);
                if (gotoGoogle.resolveActivity(getPackageManager()) != null) {
                    startActivity(gotoGoogle);
                }
            }
        });
```
In this code excerpt, the intent is not to shift to another ActivityLayout, but to move to a WebView Layout via goToGoogle
intent. If the layout is created successfully (!= null), then start the intent.

### RecyclerView
In pre-2015, the developers used to populate the entire large datalist on a single screen, which gave problems such as 
phone lagginess when you scroll the phone too quickly. The way developers populate your app view, for example, whatsapp
messages is to present them on listviews. Such a method is computationally expensive, especially if you only need to access
the top few messages without even scrolling to the bottom. That also meant huge battery drainage (vampire energy).

In 2015, the overhaul of listviews to change to recyclerviews came. Serious developers working on applications made the
switch to RecyclerView to improve app performance. Some of the benefits would be:

1. **Reuses cells while scrolling up/down** - this is possible with implementing View Holder in the ListView adapter, but it 
was an optional thing, while in the RecycleView it's the default way of writing adapter.
2. **Decouples list from its container** - so you can put list items easily at run time in the different containers 
(linearLayout, gridLayout) with setting LayoutManager.
3. **Animates common list actions** - Animations are decoupled and delegated to ItemAnimator.

In short, RecyclerView was a major change to how data is presented.
1. **Benefits of reusing cells while scrolling up/down** - If you generate the view at runtime, as your user scrolls, there
is less memory consumption, and the views that are scrolled out of range will be recycled and reused when the user scrolls
back to the position.
2. **Benefits of decoupling list from its container** - If the user prefers other layouts (GridLayout/StaggeredGridLayout),
devs do not need to recreate these from scratch. They just change the container, it's really easy!
3. **Benefits of animating common list actions** - If you can slide right and delete stuff, it is the actual effects of 
a recyclerview, listview makes it more difficult to create animations. Listview is built as a single view, recyclerview
is built in the form of containers, so removing a container is easier than deleting the whole view and recreating a view
with the deleted item absent.

My take on RecyclerView:
* When starting off, I created most of my data on listview, because it was easier, but honestly the app sucked. After building
an app using recyclerview, the performance was significant. The huge difference was like comparing a Cherry QQ vs a Honda 
CRV. By the way, it's not a Ferrari, but it is one step closer to it.

* If you just intend to copy codes, I suggest you to copy ListView codes online, it is easier. RecyclerView codes are tailored
so the codes are one level higher. However, once you create the outline, you can reuse the codes across multiple activities/fragments,
of your choice.

* I didn't want to teach rubbish ListView, so sorry to those people who want the easier way. Go read up on that if you don't
intend to use RecyclerView!

RecyclerView starts with 1 activity/fragment, 2 classes, 2 layouts, which is why this is a "difficult" topic only for beginners.<br>

Food for thought:
* You want to populate data into your view
* You want the specific data to be created in the right container
* You want to constrain the data

To start off, you should reverse the order, so that you avoid the spamming unneccessary data into the view. Btw, I won't
copy the code here as it is too long

>Create your ListItem, and it should contain only the info that you want

>RecyclerAdapter<br>
>To send data to the right container
>* Create a ViewHolder to hold the specific info that you want
>* ```onBindViewHolder()``` to bind the info to the viewholder (if not your data would not be linked to the container)
>* ```onCreateViewHolder()``` to inflate the layout containing your info (to make your data appear)
>* ```getItemCount()``` to return the number of dataset you are inputting

>Activity<br>
>To populate the data
>* Create an ArrayList or Array to store your info
>* Create instance of recyclerview
>* Create LinearLayoutManager to create it in the form of a list
>* Create the adapter and set the adapter to the recyclerview

By the way, I did not explain every line of the code here as it would probably cause more headaches! Remember to revisit
these concepts to figure out the thought process. At some point in time, it will make sense to you!

That's All Folks!

Some challenges that you guys might want to try:
* Creating a whatsapp/telegram messaging styled app
* Creating your own resume portfolio on android

*Some of us feel that the Day 2 content is more difficult than Day 3, but I felt that it was straightforward, with less
stuff to consider. I found that Day 3 content was harder for me to prepare*