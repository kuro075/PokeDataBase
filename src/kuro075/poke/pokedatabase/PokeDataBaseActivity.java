package kuro075.poke.pokedatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Sexes;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager.ViewableInformations;
import kuro075.poke.pokedatabase.util.Utility;
import kuro075.poke.pokedatabase.util.Utility.FileNames;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PokeDataBaseActivity extends Activity {
	private static final String TAG="PokeDataBaseActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	Log.v(TAG,"onCreate");
        
        int version=0;
        try{
    		PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
    		version=packageInfo.versionCode;
    	}catch(NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	final boolean new_version=false;
       	for(FileNames fn : FileNames.values()){
       		if(Utility.DEBUG || 	//デバッグ用 trueで必ずファイル更新
       		   !new_version ||	//最新バージョンかどうか
        	   !PokeDataBaseActivity.this.getFileStreamPath(fn.toString()).exists())//ファイルが存在するか
        	{
	       		try{       			
	       			copyData(fn.toString());
	    	    }catch(IOException e){
	    			Log.e("copyData",fn.toString()+"_writeerror",e);
	    	    }
	       	}
        }
       	Log.v(TAG,"Num:"+PokeDataManager.INSTANCE.getNum());
        ((TextView)findViewById(R.id.hello)).setText(new kuro075.poke.pokedatabase.data_base.poke.Test().getTestString());
        		//new kuro075.poke.pokedatabase.data_base.item.Test().getTestString());
        		//new kuro075.poke.pokedatabase.data_base.character.Test().getTestString());
        		//new kuro075.poke.pokedatabase.data_base.skill.Test().getTestString());
       	
    }
    
    private void copy2Apk(InputStream input,String file) throws IOException{
        Log.v(TAG, "copy2Apk@"+file);
    	FileOutputStream fos = PokeDataBaseActivity.this.openFileOutput(file, 0);
    	final int DEFAULT_BUFFER_SIZE = 1024*4;
    	byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    	int n=0;
    	while(-1!=(n=input.read(buffer))){
    		fos.write(buffer,0,n);
    	}
    	fos.close();
    }
    /**
     * assets内のデータを展開
     * @throws IOException
     */
    private final void copyData(String filename) throws IOException{
        Log.v(TAG, "CopyData");

        //データファイルを展開
        AssetManager as = getResources().getAssets();

        InputStream is = getAssets().open(filename);//as.open(toFile);
        copy2Apk(is,filename);
        //=====
	}
}