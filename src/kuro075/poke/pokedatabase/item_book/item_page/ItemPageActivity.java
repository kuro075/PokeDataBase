package kuro075.poke.pokedatabase.item_book.item_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.item.ItemData;
import kuro075.poke.pokedatabase.data_base.item.ItemDataManager;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.viewable_informations.ItemViewableInformations;
import kuro075.poke.pokedatabase.menu.book.ItemBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity;
import kuro075.poke.pokedatabase.item_book.item_page.ItemPageActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class ItemPageActivity extends ItemBookMenuActivity{
	private static final String TAG="ItemPageActivity";
	private static final String PACKAGE="kuro075.poke.pokedatabase.item_book.item_page";
	private static final String KEY_ITEM_NAME=PACKAGE+"."+TAG;
	
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param item_name
	 */
	public static void startThisActivity(Context context,String item_name){
		Utility.log(TAG, "startThisActivity");
		Intent intent = new Intent(context,ItemPageActivity.class);
		intent.putExtra(KEY_ITEM_NAME, item_name);
		DataStore.DataTypes.ITEM.getHistoryStore().addPageData(item_name);//履歴に保存
		context.startActivity(intent);
	}
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param item
	 */
	public static void startThisActivity(Context context,ItemData item){
		startThisActivity(context,item.toString());
	}
	
	/**
	 * このアクティビティーを履歴に保存せずにstartさせる
	 * @param context
	 * @param item_name
	 */
	public static void startThisActivityWithoutHistory(Context context,String item_name){
		Utility.log(TAG,"startThisActivity without history");
		Intent intent = new Intent(context,ItemPageActivity.class);
		intent.putExtra(KEY_ITEM_NAME, item_name);
		context.startActivity(intent);
	}
	
	/*================/
	/  インスタンス変数  /
	/================*/
	private ItemData item=ItemDataManager.INSTANCE.NullData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_page_layout);
		Utility.log(TAG,"onCreate");
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			item=ItemDataManager.INSTANCE.getItemData(extras.getString(KEY_ITEM_NAME));
		}
		
		/* レイアウト初期化 */
		
		/*================/
		/  画面上部を初期化  /
		/================*/
		initTopInfoBar();
		
		/* 買値から効果まで */
		initBasicInfo();
		/* 入手場所 */
		initGettingPlace();
		/* 持っているポケモン */
		initNumHavingPoke();
		/* 似ているアイテム */
		//TODO 似ているアイテム
	}
	
	/**
	 * 持っているポケモン
	 */
	private void initNumHavingPoke(){
		//持っているポケモン
		TextView tv=(TextView)findViewById(R.id.item_having_poke);
		tv.setText(ItemViewableInformations.NUM_HAVING_POKE.getInformation(item));
		final Context context=this;
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.ITEM, item.toString());
			}
		});
		
		//通常
		tv=(TextView)findViewById(R.id.text_normal_num);
		tv.setText(ItemViewableInformations.NUM_NORMAL_HAVING_POKE.getInformation(item));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.ITEM, item.toString()+" 通常");
			}
		});
		
		//レア
		tv=(TextView)findViewById(R.id.text_rare_num);
		tv.setText(ItemViewableInformations.NUM_RARE_HAVING_POKE.getInformation(item));
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PokeSearchResultActivity.startThisActivityWithDefaultSearch(context, PokeSearchableInformations.ITEM, item.toString()+" レア");
			}
		});
	}
	
	
	/**
	 * 入手場所(BW)
	 */
	private void initGettingPlace(){
		//入手場所(BW)
		((TextView)findViewById(R.id.text_getting_place_bw)).setText(ItemViewableInformations.GETTING_PLACE.getInformation(item));
		
	}
	
	
	/**
	 * 買値から効果までの基本データを初期化
	 */
	private void initBasicInfo(){
		((TableLayout)findViewById(R.id.tablelayout)).setStretchAllColumns(true);
		//買値
		((TextView)findViewById(R.id.text_buy)).setText(ItemViewableInformations.BUY_VALUE.getInformation(item));
		//売値
		((TextView)findViewById(R.id.text_sell)).setText(ItemViewableInformations.SELL_VALUE.getInformation(item));
		//分類
		((TextView)findViewById(R.id.text_class)).setText(ItemViewableInformations.CLASS.getInformation(item));
		//サブ分類
		((TextView)findViewById(R.id.text_sub_class)).setText(ItemViewableInformations.SUB_CLASS.getInformation(item));
		
		//使用時の効果
		((TextView)findViewById(R.id.text_using_effect)).setText(ItemViewableInformations.USING_EFFECT.getInformation(item));
		//所持時の効果
		((TextView)findViewById(R.id.text_having_effect)).setText(ItemViewableInformations.HAVING_EFFECT.getInformation(item));
	}
	
	
	/**
	 * 画面上部を初期化
	 */
	private void initTopInfoBar(){
		Utility.log(TAG,"initTopInfoBar");
		
		//名前
		((TextView)findViewById(R.id.text_name)).setText(item.getName());
		//prevボタン
		TextView text_prev=(TextView)findViewById(R.id.prev);
		text_prev.setText("<<");
		text_prev.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickPrev();
			}
		});
		//nextボタン
		((TextView)findViewById(R.id.next)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickNext();
			}
		});
	}
	
	/**
	 * <<ボタンを押したときの動作
	 */
	private void clickPrev(){
		Utility.log(TAG,"clickPrev");
		int no=item.getNo()-1;
		if(no<0){
			no=ItemDataManager.INSTANCE.getNum()-1;
		}
		startThisActivity(this,ItemDataManager.INSTANCE.getItemData(no));
	}
	
	/**
	 * >>ボタンを押した時の動作
	 */
	private void clickNext(){
		Utility.log(TAG,"clickNext");
		int no=item.getNo()+1;
		if(no==ItemDataManager.INSTANCE.getNum()){
			no=0;
		}
		startThisActivity(this,ItemDataManager.INSTANCE.getItemData(no));
	}

}
