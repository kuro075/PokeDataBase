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
import kuro075.poke.pokedatabase.data_base.poke.searchable_informations.SearchableInformations;
import kuro075.poke.pokedatabase.data_base.poke.viewable_informations.ViewableInformations;
import kuro075.poke.pokedatabase.menu.MenuItems;
import kuro075.poke.pokedatabase.menu.poke_book.PokeBookMenuActivity;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
					openDialog(prev_open);
				}else{
					setSearchIf(search_if);
					refreshListView(search_if);
				}
			}
		}
		final Context context;
		final AlertDialog operate_dialog,//操作ダイアログ
						  filter_dialog,//絞込ダイアログ
						  add_dialog,//追加ダイアログ
						  remove_dialog,//除外ダイアログ
						  view_change_dialog;//表示切替ダイアログ
		SearchIfListener listener = new MySearchIfReceiver();
		SearchTypes prev_open=SearchTypes.FILTER;
		/**
		 * コンストラクタ
		 * 全ダイアログを初期化
		 */
		private DialogManager(Context context){
			this.context=context;
			operate_dialog=getOperateDialog();
			filter_dialog=getDialog(SearchTypes.FILTER);
			add_dialog=getDialog(SearchTypes.ADD);
			remove_dialog=getDialog(SearchTypes.REMOVE);
			view_change_dialog=getViewChangeDialog();
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
					SearchableInformations.values()[position].openDialog(context, poke_list, search_type, listener);
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
			builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
				
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
			builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
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
		 * 操作ダイアログを開く
		 */
		private void openOperateDialog(){
			operate_dialog.show();
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
	
	private SortTypes sort_type=SortTypes.NO;
	private boolean flag_reverse=false;//逆順にソートするかどうか
	private ViewableInformations view_info;//表示する情報
	
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
		builder.setPositiveButton("閉じる", new DialogInterface.OnClickListener() {
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
	 * ポケモンリストを長押しした時の動作
	 * @author sanogenma
	 *
	 */
	private void longClickPokeListItem(int position){
		Utility.log(TAG, "longClickPokeListItem");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result_layout);
		Utility.log(TAG, "onCreate");
		dialog_manager=new DialogManager(this);
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
		list_view_poke=(ListView)findViewById(R.id.list_view_poke);
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
		MenuItems.POKE_SEARCH_RESULT_VIEW_CHANGE.addMenuItem(menu);
		MenuItems.POKE_SEARCH_RESULT_OPERATE.addMenuItem(menu);
		MenuItems.POKE_SEARCH_RESULT_SAVE.addMenuItem(menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(MenuItems.fromId(item.getItemId())){
			case POKE_SEARCH_RESULT_VIEW_CHANGE://表示切替
				dialog_manager.openViewChangeDialog();
				break;
			case POKE_SEARCH_RESULT_OPERATE://操作
				dialog_manager.openOperateDialog();
				break;
			case POKE_SEARCH_RESULT_SAVE://保存
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * リストビューを更新
	 */
	private void refreshListView(String toast){
		Utility.log(TAG,"refreshListView");
		
		sortPokeList();
		
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
				clickPokeListItem(position);
			}
		});
		list_view_poke.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				longClickPokeListItem(position);
				return true;
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
		refreshTitle();
	}
	
	/**
	 * タイトルを更新
	 * @param poke_num
	 */
	private void refreshTitle(){
		StringBuilder sb=new StringBuilder();
		sb.append(title);
		sb.append("(");
		sb.append(poke_list.length);
		sb.append("匹)");
		text_title.setText(new String(sb));
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
	 * ポケモンリストをソート
	 */
	private void sortPokeList(){
		switch(sort_type){
			case NO:
				Arrays.sort(poke_list, new Comparator<PokeData>(){
						@Override
					public int compare(PokeData p1, PokeData p2) {
						// TODO Auto-generated method stub
						return p1.getNo()-p2.getNo();
					}
				});
				break;
			case NAME:
				Arrays.sort(poke_list);
				break;
			case INFO:
				Arrays.sort(poke_list, view_info.getComparator());
				break;
		}
		if(flag_reverse){//逆順にソート
			Utility.reverseArray(poke_list);
		}
	}
}
