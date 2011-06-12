package kuro075.poke.pokedatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kuro075.poke.pokedatabase.character_book.CharacterBookActivity;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.item_book.ItemBookActivity;
import kuro075.poke.pokedatabase.menu.DefaultMenuActivity;
import kuro075.poke.pokedatabase.poke_book.PokeBookActivity;
import kuro075.poke.pokedatabase.skill_book.SkillBookActivity;
import kuro075.poke.pokedatabase.util.Utility;
import kuro075.poke.pokedatabase.util.Utility.FileNames;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PokeDataBaseActivity extends DefaultMenuActivity {
	private static final String PACKAGE="kuro075.poke.pokedatabase";
	private static final String TAG="PokeDataBaseActivity";
	private static final String KEY_VERSION=PACKAGE+"."+TAG+".key_version";
	
	public enum Modes{
		POKE("ポケモン図鑑") {
			@Override
			public void startActivity(Context context) {
				// TODO Auto-generated method stub
				PokeBookActivity.startThisActivity(context);
			}
		},SKILL("わざ図鑑") {
			@Override
			public void startActivity(Context context) {
				// TODO Auto-generated method stub
				SkillBookActivity.startThisActivity(context);
			}
		},CHARACTER("とくせい図鑑") {
			@Override
			public void startActivity(Context context) {
				CharacterBookActivity.startThisActivity(context);
			}
		},ITEM("アイテム図鑑") {
			@Override
			public void startActivity(Context context) {
				ItemBookActivity.startThisActivity(context);
			}
		},TYPE("タイプ図鑑") {
			@Override
			public void startActivity(Context context) {
				// TODO Auto-generated method stub
				
			}
		},SIMULATOR("シミュレーター") {
			@Override
			public void startActivity(Context context) {
				// TODO Auto-generated method stub
				
			}
		};
		private final String name;
		Modes(String name){this.name=name;}
		@Override
		public String toString(){return name;}
		
		abstract public void startActivity(Context context);
	}
	
	private class MyClickListener implements OnClickListener{
		private final Context context;
		MyClickListener(Context context){
			this.context=context;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			for(int i=0,n=button.length;i<n;i++){
				if(button[i]==v){
					Modes.values()[i].startActivity(context);
				}
			}
		}
	}
	
	Button[] button=new Button[Modes.values().length];
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	Utility.log(TAG,"onCreate");
    	/*==============/
    	/  バージョン確認  /
    	/==============*/
        int version=0;
        try{
    		PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
    		version=packageInfo.versionCode;
    	}catch(NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	SharedPreferences preference=this.getPreferences(MODE_PRIVATE);
    	final boolean new_version=version==preference.getInt(KEY_VERSION, -1);
    	Editor edit=preference.edit();
    	edit.putInt(KEY_VERSION, version);
    	edit.commit();
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
       	Utility.log(TAG,"Num of Poke:"+PokeDataManager.INSTANCE.getNum());
       	
       	final OnClickListener listener=new MyClickListener(this);
       	LinearLayout linear=(LinearLayout)findViewById(R.id.linear_layout);
       	//レイアウトの登録
       	for(int i=0,n=Modes.values().length;i<n;i++){
       		button[i]=new Button(linear.getContext());
       		button[i].setText(Modes.values()[i].toString());
       		button[i].setOnClickListener(listener);
       		linear.addView(button[i]);
       	}
    }
    
    private void copy2Apk(InputStream input,String file) throws IOException{
        Utility.log(TAG, "copy2Apk@"+file);
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
        Utility.log(TAG, "CopyData");

        //データファイルを展開
        AssetManager as = getResources().getAssets();

        InputStream is = getAssets().open(filename);//as.open(toFile);
        copy2Apk(is,filename);
        //=====
	}
}