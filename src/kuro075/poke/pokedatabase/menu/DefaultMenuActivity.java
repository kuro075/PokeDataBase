package kuro075.poke.pokedatabase.menu;

import kuro075.poke.pokedatabase.PokeDataBaseActivity;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DefaultMenuActivity extends Activity{
	private static final String TAG="DefaultMenuActivity";
	
	/**
	 * メニューを作成
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItems.MODE.addMenuItem(menu);
		MenuItems.PREFERENCE.addMenuItem(menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * メニュー項目が選択された時のメソッド
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(MenuItems.fromId(item.getItemId())){
			case MODE://モードきりかえ
				openModeSelectDialog();
				break;
			case PREFERENCE://設定
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private AlertDialog mode_dialog=null;
	/**
	 * モード選択ダイアログ
	 */
	private void openModeSelectDialog(){
		Utility.log(toString(),"openDialog");
		if(mode_dialog==null){
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(this);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.mode_select));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.center_list_item,Utility.changeToStringArray(PokeDataBaseActivity.Modes.values()));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			final Context context=this;
			//リストを選択した時の動作
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Utility.log(TAG,"onItemClick");
					PokeDataBaseActivity.Modes.values()[position].startActivity(context);
					mode_dialog.dismiss();
				}
			});
			
			//閉じるボタン
			builder.setPositiveButton(getString(R.string.close),new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			mode_dialog=builder.create();
		}
		mode_dialog.show();
	}
}
