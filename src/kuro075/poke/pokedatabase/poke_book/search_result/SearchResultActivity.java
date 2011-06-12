package kuro075.poke.pokedatabase.poke_book.search_result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.poke.SearchableInformations;
import kuro075.poke.pokedatabase.data_base.store.DataStore;
import kuro075.poke.pokedatabase.data_base.viewable_informations.ViewableInformations;
import kuro075.poke.pokedatabase.menu.MenuItems;
import kuro075.poke.pokedatabase.menu.book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 検索結果アクティビティー（ポケモン図鑑）
 * @author sanogenma
 *
 */
public class SearchResultActivity extends PokeBookMenuActivity{

	/*===========/
	/  ダイアログ  /
	/===========*/
	private class DialogManager{

		final Context context;
		final AlertDialog operate_dialog,//操作ダイアログ
						  filter_dialog,//絞込ダイアログ
						  add_dialog,//追加ダイアログ
						  remove_dialog,//除外ダイアログ
						  view_change_dialog,//表示切替ダイアログ
						  long_click_dialog;//リストを長押ししたときのダイアログ
		ListView list_view_for_long_click_dialog;//long_click_dialogのリストビュー
		
		SearchIfListener listener;
		SearchTypes prev_open=SearchTypes.FILTER;

		//除外or登録　選択ダイアログを表示
		private final String[] long_click_dialog_list_items={"除外","登録"};
		
		/**
		 * コンストラクタ
		 * 全ダイアログを初期化
		 */
		private DialogManager(Context context,SearchIfListener listener){
			this.listener=listener;
			this.context=context;
			operate_dialog=getOperateDialog();
			filter_dialog=getDialog(SearchTypes.FILTER);
			add_dialog=getDialog(SearchTypes.ADD);
			remove_dialog=getDialog(SearchTypes.REMOVE);
			view_change_dialog=getViewChangeDialog();
			long_click_dialog=getLongClickDialog();
		}
		/**
		 * (絞込・追加・除外)ダイアログを作成・取得
		 * @param type
		 * @return
		 */
		private AlertDialog getDialog(SearchTypes type){
			Utility.log(TAG, "getDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(type.toString());
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,SearchableInformations.toStringArray());
			final ListView list_view = (ListView)layout.findViewById(R.id.list_view);
			list_view.setAdapter(adapter);
			final SearchTypes search_type=type;
			list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					SearchableInformations.values()[position].openDialog(context, search_type, listener);
					switch(search_type){
						case FILTER:filter_dialog.dismiss();break;
						case ADD:	add_dialog.dismiss();	break;
						case REMOVE:remove_dialog.dismiss();break;
					}
				}
			});
			builder.setPositiveButton(getString(R.string.back), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					operate_dialog.show();
					dialog.dismiss();
				}
			});
			return builder.create();
		}
		/**
		 * ポケモンリストを長押しした時に表示するダイアログ
		 * @return
		 */
		private AlertDialog getLongClickDialog(){
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,long_click_dialog_list_items);
			list_view_for_long_click_dialog = (ListView) layout.findViewById(R.id.list_view);
			list_view_for_long_click_dialog.setAdapter(adapter);
			//閉じるボタン
			builder.setPositiveButton("閉じる",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			return builder.create();
		}
		
		/**
		 * 操作ダイアログの作成・取得
		 * @return
		 */
		private AlertDialog getOperateDialog(){
			Utility.log(TAG, "getOperateDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(getString(R.string.operate_dialog_name));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,SearchTypes.toStringArray());
			final ListView list_view = (ListView)layout.findViewById(R.id.list_view);
			list_view.setAdapter(adapter);
			list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					openDialog(SearchTypes.fromIndex(position));
					operate_dialog.dismiss();
				}
			});
			builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			return builder.create();
		}

		/**
		 * 表示切替ダイアログを作成・取得
		 * @return
		 */
		private AlertDialog getViewChangeDialog(){
			Utility.log(TAG, "getViewChangeDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(getString(R.string.view_change));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,ViewableInformations.toStringArray());
			final ListView list_view = (ListView)layout.findViewById(R.id.list_view);
			list_view.setAdapter(adapter);
			list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					clickViewChangeItem(ViewableInformations.values()[position]);
					view_change_dialog.dismiss();
				}
			});
			builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			return builder.create();
		}
		
		/**
		 * (絞込・追加・除外)ダイアログを開く
		 * @param type
		 */
		private void openDialog(SearchTypes type){
			Utility.log(TAG, "openDialog");
			prev_open=type;
			switch(type){
				case FILTER:
					filter_dialog.show();
					break;
				case ADD:
					add_dialog.show();
					break;
				case REMOVE:
					remove_dialog.show();
					break;
			}
		}
		
		/**
		 * リストを長押しした時に表示するダイアログ
		 * タイトル、リストを選択した時のリスナーを設定後、ダイアログを開く
		 * @param target
		 */
		private void openLongClickDialog(final PokeData target){
			StringBuilder sb=new StringBuilder();
			sb.append("「");
			sb.append(target.toString());
			sb.append("」をどうする?");
			long_click_dialog.setTitle(new String(sb));
			list_view_for_long_click_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Utility.log(TAG,"onItemClick");
					if(long_click_dialog_list_items[position].equals("除外")){
						StringBuilder sb=new StringBuilder();
						sb.append("「");
						sb.append(target.toString());
						sb.append("」を除外しますか？");
						Utility.openCheckDialog(context, new String(sb), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								String search_if=SearchIf.getRemoveIf(target);
								setSearchIf(search_if);
								StringBuilder sb=new StringBuilder();
								sb.append("「");
								sb.append(target.toString());
								sb.append("」を除外しました。");
								
								refreshListView(new String(sb));
								dialog.dismiss();
							}
						});
					}else
						/*
						 * お気に入りに登録or手持ち管理に登録
						 */
					if(long_click_dialog_list_items[position].equals("登録")){
						//お気に入りに登録
						DataStore.DataTypes.POKEMON.openSaveStarDialog(context, target.toString());
					}
					long_click_dialog.dismiss();
				}
			});
			long_click_dialog.show();
		}
		/**
		 * 操作ダイアログを開く
		 */
		private void openOperateDialog(){
			operate_dialog.show();
		}
		/**
		 * 直前に開いていたダイアログを開く
		 */
		private void openPrevDialog(){
			openDialog(prev_open);
		}
		
		/**
		 * 表示切替ダイアログを開く
		 */
		private void openViewChangeDialog(){
			Utility.log(TAG, "openViewChangeDialog");
			view_change_dialog.show();
		}
	}
	/**
	 * 複数選択モード
	 * @author sanogenma
	 *
	 */
	enum ListChoiceModes{
		MULTIPLE,SINGLE;
	}
	
	/**
	 * 検索条件受信クラス
	 * @author sanogenma
	 *
	 */
	private class MySearchIfReceiver implements SearchIfListener{
		@Override
		public void receiveSearchIf(String search_if) {
			// TODO Auto-generated method stub
			Utility.log(TAG, "receiveSearchIf");
			if(search_if==null){
				dialog_manager.openPrevDialog();
			}else{
				setSearchIf(search_if);
				refreshListView(search_if);
			}
		}
	}
	
	/*=======/
	/  enum  /
	/=======*/
	/**
	 * ソート
	 * @author sanogenma
	 *
	 */
	enum SortTypes{
		NO("図鑑No"),NAME("名前"),INFO("情報");
		private final String name;
		SortTypes(String name){this.name=name;}
		@Override
		public String toString(){return name;}
	}
	
	/*=========/
	/  static  / 
	/=========*/
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book.search_result";
	private static final String TAG="SearchResultActivity";
	
	private static final String KEY_SEARCH_IF=PACKAGE+"."+TAG+".search_if",
								KEY_TITLE=PACKAGE+"."+TAG+".title";
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件なし
	 * @param context
	 */
	public static void startThisActivity(Context context){
		Utility.log(TAG, "startThisActivity with no search_if");
		startThisActivity(context,"全ポケモン",new String[0]);
	}
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件が一つのとき
	 * @param context
	 * @param title
	 * @param search_if
	 */
	public static void startThisActivity(Context context,String title,String search_if){
		Utility.log(TAG, "startThisActivity with a search_if");
		String[] ifs=new String[1];
		ifs[0]=search_if;
		startThisActivity(context,title,ifs);
	}
	
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件が複数のとき
	 * @param context
	 * @param title
	 * @param search_ifs
	 */
	public static void startThisActivity(Context context,String title,String[] search_ifs){
		Utility.log(TAG, "startThisActivity with search_ifs");
		Intent intent = new Intent(context,SearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
		if(search_ifs.length>0){
			DataStore.DataTypes.POKEMON.getHistoryStore().addSearchDataWithoutTitle(search_ifs);
		}
	}
	/**
	 * このアクティビティーをstartさせる
	 * 検索条件がデフォルトのとき
	 * @param context　
	 * @param info　検索項目
	 * @param _case 検索条件
	 */
	public static void startThisActivityWithDefaultSearch(Context context,SearchableInformations info,String _case){
		Utility.log(TAG, "startThisActivityWithDefaultSearch");
		String[] ifs=new String[1];
		ifs[0]=info.getDefaultSearchIf(_case);
		startThisActivity(context,info.getDefaultTitle(_case),ifs);
	}
	/**
	 * このアクティビティーをstartさせる
	 * 履歴を保存しない
	 * @param context
	 * @param title
	 * @param search_ifs
	 */
	public static void startThisActivityWithoutHistory(Context context,String title,String[] search_ifs){
		Utility.log(TAG, "startThisActivityWithoutHistory");
		Intent intent = new Intent(context,SearchResultActivity.class);
		intent.putExtra(KEY_TITLE, title);
		intent.putExtra(KEY_SEARCH_IF, search_ifs);
		context.startActivity(intent);
	}
	/*================/
	/  インスタンス変数  /
	/================*/
	private String title;//タイトル
	private TextView text_title,text_detail_if,text_no,text_name,text_info;
	private ListView list_view_poke;
	
	private List<String> search_ifs=new ArrayList<String>();//検索条件
	
	private List<String[]> prev_search_ifs=new ArrayList<String[]>();//Undo用の検索条件

	private PokeData[] poke_list;//検索結果ポケモンリスト
	
	private int prev_poke_num;//前回のポケモンの数,リストをリフレッシュするごとに更新
	
	private SortTypes sort_type=SortTypes.NO;//ソートタイプ
	private boolean flag_reverse=false;//逆順にソートするかどうか
	private ViewableInformations view_info;//表示する情報
	
	private ListChoiceModes choice_mode=ListChoiceModes.SINGLE;//リストビュー選択モード
	
	private DialogManager dialog_manager;
	
	/**
	 * 詳細条件をクリックしたとき
	 * @author sanogenma
	 *
	 */
	private void clickDetailIf(){
		Utility.log(TAG, "clickDetailIf");
		AlertDialog.Builder builder;
		AlertDialog alertDialog;
		
		LayoutInflater factory=LayoutInflater.from(this);
		final View layout = factory.inflate(R.layout.detail_if_dialog,null);
		
		builder = new AlertDialog.Builder(this);
		builder.setTitle("検索条件詳細");
		builder.setView(layout);
		final LinearLayout linearlayout=(LinearLayout)layout.findViewById(R.id.layout);
		for(String _if:search_ifs){
			TextView tv=new TextView(linearlayout.getContext());
			tv.setText(_if);
			tv.setTextSize(17.0f);
			linearlayout.addView(tv);
		}
		final Context context=this;
		if(search_ifs.size()>0){
			builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					DataStore.DataTypes.POKEMON.openSaveShortCutDialog(context, search_ifs.toArray(new String[0]));
					dialog.dismiss();
				}
			});
		}
		
		builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	/**
	 * リストの列名ラベルをクリックした時
	 * @author sanogenma
	 *
	 */
	private void clickLabel(SortTypes sort_type){
		Utility.log(TAG, "clickLabel");
		if(choice_mode==ListChoiceModes.SINGLE){
			flag_reverse=this.sort_type.equals(sort_type)?!flag_reverse:false;
			this.sort_type=sort_type;
			StringBuilder toast=new StringBuilder();
			if(sort_type==SortTypes.INFO) toast.append(view_info.toString());
			else /*図鑑No or 名前の場合*/	  toast.append(sort_type.toString());
			toast.append("で");
			if(flag_reverse)toast.append("逆順に");
			toast.append("ソートしました");
			refreshListView(new String(toast));
		}
	}
	
	
	/**
	 * ポケモンリストをクリックした時の動作
	 * @author sanogenma
	 *
	 */
	private void clickPokeListItem(int position){
		Utility.log(TAG, "clickPokeListItem");
		PokePageActivity.startThisActivity(this, poke_list[position]);
	}
	
	/**
	 * 表示切替ダイアログの項目をクリックした時の動作
	 * @param info
	 */
	private void clickViewChangeItem(ViewableInformations info){
		this.view_info=info;
		StringBuilder sb=new StringBuilder();
		sb.append(info);
		sb.append("に切り替えました。");
		refreshListView(new String(sb));
	}
	
	/**
	 * 選択されたポケモンのリストを取得
	 * @return
	 */
	private PokeData[] getSelectedPokeList(){
		if(choice_mode==ListChoiceModes.MULTIPLE){
			SparseBooleanArray checked =list_view_poke.getCheckedItemPositions();
			List<PokeData> remove_list=new ArrayList<PokeData>();
			for(int i=0,n=checked.size();i<n;i++){
				if(checked.valueAt(i)){
					remove_list.add(poke_list[checked.keyAt(i)]);
				}
			}
			return remove_list.toArray(new PokeData[0]);
		}
		return new PokeData[0];
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		switch(choice_mode){
			case SINGLE://通常時
				Utility.openCheckDialog(this, "検索結果を終了してよろしいですか？", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				//super.onBackPressed();
				break;
			case MULTIPLE://複数選択モード時
				this.shiftSingleChoiceMode(null);
				break;
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result_layout);
		Utility.log(TAG, "onCreate");
		dialog_manager=new DialogManager(this,new MySearchIfReceiver());
		/*=================/
		/  ウィジェットの登録  /
		/=================*/
		//タイトル
		text_title=(TextView)findViewById(R.id.text_title);
		//条件詳細
		text_detail_if=(TextView)findViewById(R.id.text_detail);
		text_detail_if.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickDetailIf();
			}
		});
		//No
		text_no=(TextView)findViewById(R.id.text_no);
		text_no.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickLabel(SortTypes.NO);
			}
		});
		//名前
		text_name=(TextView)findViewById(R.id.text_name);
		text_name.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickLabel(SortTypes.NAME);
			}
		});
		//情報
		text_info=(TextView)findViewById(R.id.text_info);
		text_info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickLabel(SortTypes.INFO);
			}
		});
		//リスト
		list_view_poke=(ListView)findViewById(R.id.list_view);
		/*======================/
		/  インスタンス変数初期設定  /
		/======================*/
		poke_list=PokeDataManager.INSTANCE.getAllPokeData();//ポケモンリスト
		prev_poke_num=poke_list.length;//ポケモンの数
		view_info=ViewableInformations.TYPE;
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			title=extras.getString(KEY_TITLE);//タイトル
			final String[] ifs=extras.getStringArray(KEY_SEARCH_IF);//条件
			for(String _if:ifs){
				setSearchIf(_if);
			}
		}
		refreshListView(title);
	}
	
	/**
	 * メニューを作成
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItems.SEARCH_RESULT_VIEW_CHANGE.addMenuItem(menu);//表示切替
		MenuItems.SEARCH_RESULT_OPERATE.addMenuItem(menu);//操作
		MenuItems.UNDO.addMenuItem(menu);//元に戻す
		MenuItems.SEARCH_RESULT_SAVE.addMenuItem(menu);//ショートカットに登録
		MenuItems.PAGE_SAVE.addMenuItem(menu);//お気に入りに登録
		MenuItems.SEARCH_RESULT_REMOVE.addMenuItem(menu);//除外
		MenuItems.SEARCH_RESULT_MULTIPLE_SELECT.addMenuItem(menu);//複数選択
		MenuItems.SEARCH_RESULT_SINGLE_SELECT.addMenuItem(menu);//複数選択 解除
		return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * メニューアイテムが選択された時の動作
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(MenuItems.fromId(item.getItemId())){
			case SEARCH_RESULT_VIEW_CHANGE://表示切替
				dialog_manager.openViewChangeDialog();
				break;
			case SEARCH_RESULT_OPERATE://操作
				dialog_manager.openOperateDialog();
				break;
			case UNDO://元に戻す
				undo();
				break;
			case SEARCH_RESULT_SAVE://ショートカットに登録
				getDataType().openSaveShortCutDialog(this, search_ifs.toArray(new String[0]));
				break;
			case PAGE_SAVE://お気に入りに登録
				getDataType().openSaveStarDialog(this, Utility.changeToStringArray(getSelectedPokeList()));
				//shiftSingleChoiceMode(null);
				break;
			case SEARCH_RESULT_REMOVE://除外
				final PokeData[] remove_list=this.getSelectedPokeList();
				StringBuilder sb=new StringBuilder();
				for(int i=0,n=remove_list.length;i<n;i++){
					sb.append(remove_list[i].toString());
					if(i!=n-1){
						sb.append("・");
					}
				}
				//確認ダイアログを開く
				Utility.openSimpleTextDialog(this,"除外しますか？", new String(sb), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						StringBuilder sb=new StringBuilder();
						for(int i=0,n=remove_list.length;i<n;i++){
							sb.append(remove_list[i].toString());
							if(i!=n-1){
								sb.append("・");
							}
						}
						sb.append("を除外しました");
						setSearchIf(SearchIf.getRemoveIf(remove_list));
						shiftSingleChoiceMode(new String(sb));
					}
				});
				break;
			case SEARCH_RESULT_MULTIPLE_SELECT:
				this.shiftMultipleChoiceMode();
				break;
			case SEARCH_RESULT_SINGLE_SELECT:
				shiftSingleChoiceMode(null);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		switch(choice_mode){
			case SINGLE://通常時
				menu.findItem(MenuItems.SEARCH_RESULT_VIEW_CHANGE.getId()).setVisible(true);//表示切替
				menu.findItem(MenuItems.SEARCH_RESULT_OPERATE.getId()).setVisible(true);//操作
				menu.findItem(MenuItems.UNDO.getId()).setVisible(this.prev_search_ifs.size()>0);//元に戻す
				menu.findItem(MenuItems.SEARCH_RESULT_SAVE.getId()).setVisible(this.prev_search_ifs.size()>0);//ショートカットに登録
				menu.findItem(MenuItems.PAGE_SAVE.getId()).setVisible(false);//お気に入りに登録
				menu.findItem(MenuItems.SEARCH_RESULT_MULTIPLE_SELECT.getId()).setVisible(true);//複数選択
				menu.findItem(MenuItems.SEARCH_RESULT_SINGLE_SELECT.getId()).setVisible(false);//複数選択 解除
				menu.findItem(MenuItems.SEARCH_RESULT_REMOVE.getId()).setVisible(false);//除外
				break;
			case MULTIPLE://複数選択モード時
				int count=0;
				SparseBooleanArray checked = list_view_poke.getCheckedItemPositions();
				for(int i=0,n=checked.size();i<n;i++){
					if(checked.valueAt(i)) count++;
				}
				if(count==checked.size()) Utility.log(TAG,"count=checked.size");
				menu.findItem(MenuItems.SEARCH_RESULT_VIEW_CHANGE.getId()).setVisible(false);//表示切替
				menu.findItem(MenuItems.SEARCH_RESULT_OPERATE.getId()).setVisible(false);//操作
				menu.findItem(MenuItems.UNDO.getId()).setVisible(false);//元に戻す
				menu.findItem(MenuItems.SEARCH_RESULT_SAVE.getId()).setVisible(false);//ショートカットに登録
				menu.findItem(MenuItems.PAGE_SAVE.getId()).setVisible(count>0);//お気に入りに登録
				menu.findItem(MenuItems.SEARCH_RESULT_MULTIPLE_SELECT.getId()).setVisible(false);//複数選択
				menu.findItem(MenuItems.SEARCH_RESULT_SINGLE_SELECT.getId()).setVisible(true);//複数選択 解除
				menu.findItem(MenuItems.SEARCH_RESULT_REMOVE.getId()).setVisible(count>0);//除外
				break;
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	/**
	 * リストビューを更新
	 */
	private void refreshListView(String toast){
		Utility.log(TAG,"refreshListView");
		sortPokeList();
		switch(choice_mode){
			case SINGLE://通常モード
				text_info.setText(view_info.toString());//情報のラベルのテキストをセット
				//リストビューの項目を作成
				List<ListItemBean> list = new ArrayList<ListItemBean>();
				for(PokeData poke:poke_list){
					ListItemBean item =new ListItemBean();
					item.setNo(poke.getNo2String());
					item.setName(poke.getName());
					item.setInfo(view_info.getInformation(poke));
					list.add(item);
				}
				ListAdapter adapter = new ListAdapter(getApplicationContext(),list);
				list_view_poke.setAdapter(adapter);
				list_view_poke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
						// TODO Auto-generated method stub
						if(choice_mode==ListChoiceModes.SINGLE){
							clickPokeListItem(position);
						}
					}
				});
				list_view_poke.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						if(choice_mode==ListChoiceModes.SINGLE){
							dialog_manager.openLongClickDialog(poke_list[position]);
							return true;
						}
						return false;
					}
				});
				
				StringBuilder sb=new StringBuilder();
				if(toast!=null){
					sb.append(toast);
				}
				if(prev_poke_num!=poke_list.length){
					if(toast!=null) sb.append(" ");
					sb.append(prev_poke_num);
					sb.append("匹→");
					sb.append(poke_list.length);
					sb.append("匹");
				}
				Utility.popToast(this, new String(sb));
				prev_poke_num=poke_list.length;
				refreshTitle(title);
				break;
				
			case MULTIPLE://複数選択モード
				text_info.setText("選択");
				list_view_poke.setAdapter(new MultipleChoicePokeListAdapter(getApplicationContext(),Arrays.asList(poke_list)));
			
			    for (int i=0,n=poke_list.length;i<n;i++) {  
				      // 指定したアイテムがチェックされているかを設定  
				      list_view_poke.setItemChecked(i, false);  
			    } 
				refreshTitle("複数選択モード");
				if(toast!=null){
					Utility.popToast(this, toast);
				}
				else{
					Utility.popToast(this, "複数選択モード");
				}
				break;
		}
	}
	
	/**
	 * タイトルを更新
	 * @param poke_num
	 */
	private void refreshTitle(String title){
		StringBuilder sb=new StringBuilder();
		sb.append(title);
		sb.append("(");
		sb.append(poke_list.length);
		sb.append("匹)");
		text_title.setText(new String(sb));
	}
	/**
	 * 選択モードを設定する
	 * @param mode
	 */
	private void setChoiceMode(ListChoiceModes mode){
		choice_mode=mode;
	}
	
	
	
	/**
	 * 検索条件を適用
	 * @param search_if
	 */
	private void setSearchIf(String search_if){
		Utility.log(TAG, "setSearchIf");
		poke_list=SearchableInformations.searchBySearchIf(poke_list, search_if);//ポケモンリストを更新
		prev_search_ifs.add(search_ifs.toArray(new String[0]));//アンドゥ用に検索条件を保存
		search_ifs.add(search_if);//検索条件を追加
	}
	
	/**
	 * 複数選択モードに移行
	 */
	private void shiftMultipleChoiceMode(){
		Utility.log(TAG,"shiftMultipleChoiceMode");
		setChoiceMode(ListChoiceModes.MULTIPLE);
		list_view_poke.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		refreshListView("複数選択モード");
	}
	/**
	 *　単一選択モードに移行
	 *	@param toast:モード変更時に表示するテキスト　nullの場合"複数選択モード 終了"
	 */
	private void shiftSingleChoiceMode(String toast){
		Utility.log(TAG, "shiftSingleChoiceMode");
		setChoiceMode(ListChoiceModes.SINGLE);
		list_view_poke.setItemsCanFocus(true);
		list_view_poke.setChoiceMode(ListView.CHOICE_MODE_NONE);
		if(toast!=null){
			refreshListView(toast);
		}else{
			refreshListView("複数選択モード 終了");
		}
	}
	
	/**
	 * ポケモンリストをソート
	 */
	private void sortPokeList(){
		int color=choice_mode==ListChoiceModes.SINGLE?Color.rgb(255,255,255):Color.rgb(200, 200, 200);
		this.text_no.setTextColor(color);
		this.text_name.setTextColor(color);
		this.text_info.setTextColor(color);
		switch(sort_type){
			case NO:
				Arrays.sort(poke_list, new Comparator<PokeData>(){
						@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getNo()-p2.getNo();
					}
				});
				this.text_no.setTextColor(Color.rgb(255,255,204));
				
				break;
			case NAME:
				Arrays.sort(poke_list);
				this.text_name.setTextColor(Color.rgb(255,255,204));
				break;
			case INFO:
				Arrays.sort(poke_list, view_info.getComparator());
				this.text_info.setTextColor(Color.rgb(255,255,204));
				break;
		}
		if(flag_reverse){//逆順にソート
			Utility.reverseArray(poke_list);
		}
	}
	/**
	 * undoを行う
	 */
	private void undo(){
		Utility.log(TAG,"undo");
		//データ初期化
		poke_list=PokeDataManager.INSTANCE.getAllPokeData();//poke_list初期化
		for(int i=0,n=search_ifs.size();i<n;i++){
			search_ifs.remove(0);
		}
		final int last_index=prev_search_ifs.size()-1;//最新undoデータのインデックスを取得
		//最新undoデータを適用
		for(String _if:prev_search_ifs.get(last_index)){
			poke_list=SearchableInformations.searchBySearchIf(poke_list, _if);
			search_ifs.add(_if);
		}
		prev_search_ifs.remove(last_index);//最新undoデータを削除
		
		this.refreshListView("undoを実行");
	}
}
