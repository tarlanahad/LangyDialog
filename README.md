# LangyDialog
A tiny library to select Langauge

## Screenshots
![GIF](https://github.com/tarlanahad/LangyDialog/blob/master/ezgif-5-4a01b5d40b.gif)


## Usage

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  __________
  ```
  	dependencies {
	        compile 'com.github.tarlanahad:LangyDialog:1.0'
	      }
  ```
  __________
  ## In your project
  ```
  dialog = new LangyDialog(MainActivity.this, new OnLanguageSelectListener() {
                    @Override
                    public void OnLanguageSelectListener(String lang, int position) {
                        ((FontableTextView) (findViewById(R.id.tv))).setText(lang);
                        dialog.cancel();
                    }
});
  dialog.setItemsFont("Lato-Light.ttf").show(); //optional
  ```
  
