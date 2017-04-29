## Gradle:

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
	
### Step 2. Add the dependency

```
dependencies {
	        compile 'com.github.therightwaystudio:QtumSDK:v1.0'
	}
```
	
## Maven:

### Step 1. Add the JitPack repository to your build file

```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
	
### Step 2. Add the dependency

```
<dependency>
	    <groupId>com.github.therightwaystudio</groupId>
	    <artifactId>QtumSDK</artifactId>
	    <version>v1.0</version>
	</dependency>
```

## How to use

In the payment method selection menu, add the button "Qtum"
In the button event handler, add a method call to open the QtumPaymentActivity. In the method parameters add the current activity and the amount:

```
mButtonQtumPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QtumPaymentActivity.openQtumPaymentActivity(MainActivity.this,mEditTextAmount.getText().toString());
            }
        });
```