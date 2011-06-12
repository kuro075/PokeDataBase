package kuro075.poke.pokedatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.poke.PokeSearchableInformations;
import kuro075.poke.pokedatabase.data_base.viewable_informations.PokeViewableInformations;
import kuro075.poke.pokedatabase.menu.MenuItems;
import kuro075.poke.pokedatabase.menu.book.BookMenuActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class SearchResultActivity extends BookMenuActivity{
	
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
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,getSearchableInformationTitles());
			final ListView list_view = (ListView)layout.findViewById(R.id.list_view);
			list_view.setAdapter(adapter);
			final SearchTypes search_type=type;
			list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					openSearchDialog(position,context,search_type,listener);
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
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,getLongClickDialogItems());
			list_view_for_long_click_dialog = (ListView) layout.findViewById(R.id.list_view);
			list_view_for_long_click_dialog.setAdapter(adapter);
			//閉じるボタン
			builder.setPositiveButton("閉じる",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
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
					openDialog(SearchTypes.fromIndex(position));
					operate_dialog.dismiss();
				}
			});
			builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
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
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,getViewableInformationTitles());
			final ListView list_view = (ListView)layout.findViewById(R.id.list_view);
			list_view.setAdapter(adapter);
			list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					clickViewChangeItem(position);
					view_change_dialog.dismiss();
				}
			});
			builder.setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
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
		private void openLongClickDialog(final BasicData target){
			StringBuilder sb=new StringBuilder();
			sb.append("「");
			sb.append(target.toString());
			sb.append("」をどうする?");
			long_click_dialog.setTitle(new String(sb));
			list_view_for_long_click_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					Utility.log(TAG,"onItemClick");
					clickLongClickDialogItem(target,position);
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
	/*=======/
	/  enum  /
	/=======*/
	/**
	 * リストの選択モード
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
			Utility.log(TAG, "receiveSearchIf");
			if(search_if==null){
				dialog_manager.openPrevDialog();
			}else{
				addSearchIf(search_if);
				refreshListView(search_if);
			}
		}
	}
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
	private static final String PACKAGE="kuro075.poke.pokedatabase";
	private static final String TAG="SearchResultActivity";
	
	protected static final String KEY_SEARCH_IF=PACKAGE+"."+TAG+".search_if",
								  KEY_TITLE=PACKAGE+"."+TAG+".title";
	/*================/
	/  インスタンス変数  /
	/================*/
	/* ウィジェット */
	private TextView text_title,text_detail_if,text_no,text_name,text_info;
	private ListView list_view;//リスト

	/* 検索関連 */
	private BasicData[] datas;//検索後のデータ
	private List<String> search_ifs=new ArrayList<String>();//検索条件
	private List<String[]> prev_search_ifs=new ArrayList<String[]>();//Undo用検索条件保存変数
	/* 表示関連 */
	private String title;//タイトル

	private int prev_num_datas;//前回のデータの数
	private int index_viewable_informations;//表示する情報のインデックス
	/* ソート */
	private SortTypes sort_type;//ソートのインデックス
	
	private boolean flag_reverse=false;//逆順にソートするかどうか
	/* 複数選択 */
	private ListChoiceModes choice_mode=ListChoiceModes.SINGLE;
	/* ダイアログ */
	private DialogManager dialog_manager;
	
	/**
	 * 検索条件を追加
	 * @param search_if
	 */
	private void addSearchIf(String search_if){
		Utility.log(TAG, "addSearchIf");
		prev_search_ifs.add(search_ifs.toArray(new String[0]));//Undo用に保存
		search_ifs.add(search_if);//検索条件を追加
	}
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
					getDataType().openSaveShortCutDialog(context, search_ifs.toArray(new String[0]));
					dialog.dismiss();
				}
			});
		}
		
		builder.setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	/**
	 * ラベルをクリックした時の動作
	 * @param sort_type
	 */
	private void clickLabel(SortTypes sort_type){
		Utility.log(TAG, "clickLabel");
		if(choice_mode==ListChoiceModes.SINGLE){
			flag_reverse=this.sort_type.equals(sort_type)?!flag_reverse:false;
			this.sort_type=sort_type;
			StringBuilder toast=new StringBuilder();
			if(sort_type==SortTypes.INFO) toast.append(this.getViewableInformationTitle(index_viewable_informations));
			else /*図鑑No or 名前の場合*/	  toast.append(sort_type.toString());
			toast.append("で");
			if(flag_reverse)toast.append("逆順に");
			toast.append("ソートしました");
			refreshListView(new String(toast));
		}
	}

	/**
	 * リストをクリックした時の動作
	 */
	protected void clickListItem(BasicData data){
		Utility.log(TAG, "clickListItem");
	}
	/**
	 * 長押しした時のダイアログの項目をクリックした時の動作
	 * @param position
	 */
	protected void clickLongClickDialogItem(final BasicData target,int position){
		switch(position){
			case 0://除外
				StringBuilder sb=new StringBuilder();
				sb.append("「");
				sb.append(target.toString());
				sb.append("」を除外しますか？");
				Utility.openCheckDialog(this, new String(sb), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String search_if=SearchIf.getRemoveIf(target);
						addSearchIf(search_if);
						StringBuilder sb=new StringBuilder();
						sb.append("「");
						sb.append(target.toString());
						sb.append("」を除外しました。");
						
						refreshListView(new String(sb));
						dialog.dismiss();
					}
				});
				break;
			case 1://登録
				getDataType().openSaveStarDialog(this, target.toString());
				break;
		}
	}
	
	/**
	 * リストを長押しした時の動作
	 * @param position
	 */
	private void clickLongListItem(int position){
		Utility.log(TAG, "clickLongListItem");
		dialog_manager.openLongClickDialog(datas[position]);
	}
	
	/**
	 * 表示切替の項目をクリックした時の動作
	 * @param position
	 */
	private void clickViewChangeItem(int position){
		this.index_viewable_informations=position;
		StringBuilder sb=new StringBuilder();
		sb.append(getViewableInformationTitle(position));
		sb.append("に切り替えました。");
		refreshListView(new String(sb));
	}
	
	/**
	 * 全てのデータ配列を取得
	 * サブクラスでオーバーライド
	 * @return
	 */
	protected BasicData[] getAllDatas(){
		return new BasicData[0];
	}
	/**
	 * ViewableInformationのComparatorを取得
	 * @param index
	 * @return
	 */
	protected Comparator getComparatorByViewableInformation(int index){
		return null;
	}
	
	/**
	 * デフォルトのソートタイプを返す
	 * @return
	 */
	private SortTypes getDefaultSortType(){
		if(getNoVisible()) return SortTypes.NO;
		return SortTypes.NAME;
	}
	
	/**
	 * リストを長押しした時に表示するダイアログの項目
	 * @return
	 */
	protected String[] getLongClickDialogItems(){
		final String[] items={"除外","登録"};
		return items;
	}
	/**
	 * データの名前の最大長を返す
	 * @return
	 */
	protected int getMaxLengthOfName(){
		return 7;
	}
	
	/**
	 * Noを表示するかどうかを返す
	 */
	protected boolean getNoVisible(){
		return false;
	}
	
	/**
	 * SearchableInformationsのタイトルの配列を返す
	 * @return
	 */
	protected String[] getSearchableInformationTitles(){
		return new String[0];
	}
	/**
	 * 複数選択モードで選択された項目を取得
	 * @return
	 */
	private BasicData[] getSelectedItems(){
		if(choice_mode==ListChoiceModes.MULTIPLE){
			SparseBooleanArray checked =list_view.getCheckedItemPositions();
			List<BasicData> remove_list=new ArrayList<BasicData>();
			for(int i=0,n=checked.size();i<n;i++){
				if(checked.valueAt(i)){
					remove_list.add(datas[checked.keyAt(i)]);
				}
			}
			return remove_list.toArray(new BasicData[0]);
		}
		return new BasicData[0];
	}
	
	/**
	 * 表示する情報を取得
	 * @param index
	 * @param data
	 * @return
	 */
	protected String getViewableInformation(int index,BasicData data){
		return "";
	}
	/**
	 * ViewableInformationsのタイトルを取得
	 * @param index
	 * @return
	 */
	protected String getViewableInformationTitle(int index){
		return "";
	}
	/**
	 * ViewableInformationsのタイトルの配列を返す
	 * @return
	 */
	protected String[] getViewableInformationTitles(){
		return new String[0];
	}
	
	/**
	 * 戻るボタンを押した時の動作
	 */
	@Override
	public void onBackPressed() {
		switch(choice_mode){
			case SINGLE://通常時
				Utility.openCheckDialog(this, "検索結果を終了してよろしいですか？", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
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
		super.onCreate(savedInstanceState);
		Utility.log(TAG, "onCreate");
		setContentView(R.layout.search_result_layout);
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
				clickDetailIf();
			}
		});
		//No
		text_no=(TextView)findViewById(R.id.text_no);
		text_no.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickLabel(SortTypes.NO);
			}
		});
		if(!getNoVisible())text_no.setVisibility(View.GONE);
		//名前
		text_name=(TextView)findViewById(R.id.text_name);
		text_name.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickLabel(SortTypes.NAME);
			}
		});
		text_name.setMinWidth((int)text_name.getTextSize()*getMaxLengthOfName());
		//情報
		text_info=(TextView)findViewById(R.id.text_info);
		text_info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				clickLabel(SortTypes.INFO);
			}
		});
		list_view=(ListView)findViewById(R.id.list_view);
		/*======================/
		/  インスタンス変数初期設定  /
		/======================*/
		dialog_manager=new DialogManager(this,new MySearchIfReceiver());
		sort_type=getDefaultSortType();
		index_viewable_informations=0;
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			title=extras.getString(KEY_TITLE);//タイトル
			final String[] ifs=extras.getStringArray(KEY_SEARCH_IF);//条件
			for(String _if:ifs){
				addSearchIf(_if);
			}
		}
		updateDatas();
		prev_num_datas=getAllDatas().length;
		refreshListView(title);
	}
	
	/**
	 * メニューを作成
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
				getDataType().openSaveStarDialog(this, Utility.changeToStringArray(getSelectedItems()));
				//shiftSingleChoiceMode(null);
				break;
			case SEARCH_RESULT_REMOVE://除外
				final BasicData[] remove_list=this.getSelectedItems();
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
						StringBuilder sb=new StringBuilder();
						for(int i=0,n=remove_list.length;i<n;i++){
							sb.append(remove_list[i].toString());
							if(i!=n-1){
								sb.append("・");
							}
						}
						sb.append("を除外しました");
						addSearchIf(SearchIf.getRemoveIf(remove_list));
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
	
	/**
	 * 状況に応じてメニューの表示非表示切り替え
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
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
				SparseBooleanArray checked = list_view.getCheckedItemPositions();
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
	 * 検索ダイアログを開く
	 * @param context
	 * @param search_type
	 * @param listener
	 */
	protected void openSearchDialog(int index,Context context,SearchTypes search_type,SearchIfListener listener){
		Utility.log(TAG, "openSearchDialog");
	}
	
	/**
	 * リストビューを更新してポップアップを表示
	 * @param toast
	 */
	private void refreshListView(String toast){
		updateDatas();
		sort();
		switch(choice_mode){
			case SINGLE://通常モード
				text_info.setText(getViewableInformationTitle(index_viewable_informations));//情報のラベルのテキストをセット
				//リストビューの項目を作成
				List<ListItemBean> list = new ArrayList<ListItemBean>();
				for(BasicData data:datas){
					ListItemBean item =new ListItemBean();
					item.setNo(data.getNo2String());
					item.setName(data.getName());
					item.setInfo(getViewableInformation(index_viewable_informations,data));
					list.add(item);
				}
				ListAdapter adapter = new ListAdapter(getApplicationContext(),list,getNoVisible(),getMaxLengthOfName());
				list_view.setAdapter(adapter);
				list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
						if(choice_mode==ListChoiceModes.SINGLE){
							clickListItem(datas[position]);
						}
					}
				});
				list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view,
							int position, long id) {
						if(choice_mode==ListChoiceModes.SINGLE){
							clickLongListItem(position);
							return true;
						}
						return false;
					}
				});
				
				StringBuilder sb=new StringBuilder();
				if(toast!=null){
					sb.append(toast);
				}
				if(prev_num_datas!=datas.length){
					if(toast!=null) sb.append(" ");
					sb.append(prev_num_datas);
					sb.append("個→");
					sb.append(datas.length);
					sb.append("個");
				}
				Utility.popToast(this, new String(sb));
				prev_num_datas=datas.length;
				refreshTitle(title);
				break;
				
			case MULTIPLE://複数選択モード
				text_info.setText("選択");
				list_view.setAdapter(new MultipleChoicePokeListAdapter(getApplicationContext(),Arrays.asList(datas),getNoVisible(),getMaxLengthOfName()));
			
			    for (int i=0,n=datas.length;i<n;i++) {  
				      // 指定したアイテムがチェックされているかを設定  
				      list_view.setItemChecked(i, false);  
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
	 * @param title
	 */
	private void refreshTitle(String title){
		StringBuilder sb=new StringBuilder();
		sb.append(title);
		sb.append("(");
		sb.append(datas.length);
		sb.append("個)");
		text_title.setText(new String(sb));
	}

	/**
	 * search_ifによって検索を行う
	 * @param data_array
	 * @param search_if
	 * @return
	 */
	protected BasicData[] search(BasicData[] data_array,String search_if){
		return new BasicData[0];
	}
	/**
	 * 複数選択モードに移行
	 */
	private void shiftMultipleChoiceMode(){
		Utility.log(TAG,"shiftMultipleChoiceMode");
		choice_mode=ListChoiceModes.MULTIPLE;
		list_view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		refreshListView("複数選択モード");
	}
	/**
	 *　単一選択モードに移行
	 *	@param toast:モード変更時に表示するテキスト　nullの場合"複数選択モード 終了"
	 */
	private void shiftSingleChoiceMode(String toast){
		Utility.log(TAG, "shiftSingleChoiceMode");
		choice_mode=ListChoiceModes.SINGLE;
		list_view.setItemsCanFocus(true);
		list_view.setChoiceMode(ListView.CHOICE_MODE_NONE);
		if(toast!=null){
			refreshListView(toast);
		}else{
			refreshListView("複数選択モード 終了");
		}
	}
	/**
	 * データをソート
	 * @param datas
	 */
	private void sort(){
		int color=choice_mode==ListChoiceModes.SINGLE?Color.rgb(255,255,255):Color.rgb(200, 200, 200);
		this.text_no.setTextColor(color);
		this.text_name.setTextColor(color);
		this.text_info.setTextColor(color);
		switch(sort_type){
			case NO:
				Arrays.sort(datas, new Comparator<BasicData>(){
						@Override
					public int compare(BasicData b1, BasicData b2) {
						return b1.getNo()-b2.getNo();
					}
				});
				this.text_no.setTextColor(Color.rgb(255,255,204));
				break;
			case NAME:
				Arrays.sort(datas);
				this.text_name.setTextColor(Color.rgb(255,255,204));
				break;
			case INFO:
				Arrays.sort(datas, getComparatorByViewableInformation(index_viewable_informations));
				this.text_info.setTextColor(Color.rgb(255,255,204));
				break;
		}
		if(flag_reverse){//逆順にソート
			Utility.reverseArray(datas);
		}
	}
	/**
	 * undoを行う
	 */
	private void undo(){
		Utility.log(TAG,"undo");
		//データ初期化
		for(int i=0,n=search_ifs.size();i<n;i++){
			search_ifs.remove(0);
		}
		final int last_index=prev_search_ifs.size()-1;//最新undoデータのインデックスを取得
		//最新undoデータを適用
		for(String _if:prev_search_ifs.get(last_index)){
			search_ifs.add(_if);
		}
		prev_search_ifs.remove(last_index);//最新undoデータを削除
		
		refreshListView("undoを実行");
	}
	/**
	 * 検索条件を適用させる
	 */
	private void updateDatas(){
		BasicData[] new_datas=getAllDatas();
		for(String search_if:search_ifs){
			new_datas=search(new_datas,search_if);
		}
		datas=new_datas;
	}
}
