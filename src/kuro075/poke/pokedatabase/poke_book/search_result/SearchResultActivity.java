package kuro075.poke.pokedatabase.poke_book.search_result;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager.SearchableInformations;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager.ViewableInformations;
import kuro075.poke.pokedatabase.poke_book.PokeBookActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 検索結果アクティビティー（ポケモン図鑑）
 * @author sanogenma
 *
 */
public class SearchResultActivity extends Activity{

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
	
	
	private ViewableInformations view_info;//表示する情報
	
	/**
	 * 詳細条件をクリックしたとき
	 * @author sanogenma
	 *
	 */
	private void clickDetailIf(){
		Utility.log(TAG, "clickDetailIf");
	}
	/**
	 * リストの列名ラベルをクリックした時
	 * @author sanogenma
	 *
	 */
	private void clickLabel(View v){
		Utility.log(TAG, "clickLabel");
	}
	/**
	 * ポケモンリストをクリックした時の動作
	 * @author sanogenma
	 *
	 */
	private void clickPokeListItem(int position){
		Utility.log(TAG, "clickPokeListItem");
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
				clickLabel(v);
			}
		});
		//名前
		text_name=(TextView)findViewById(R.id.text_name);
		text_name.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickLabel(v);
			}
		});
		//情報
		text_info=(TextView)findViewById(R.id.text_info);
		text_info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickLabel(v);
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
		refreshListView();
	}
	
	/**
	 * リストビューを更新
	 */
	private void refreshListView(){
		Utility.log(TAG,"refreshListView");
		
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
		
		Utility.popToast(this, prev_poke_num+"匹→"+poke_list.length+"匹");
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
		poke_list=SearchableInformations.searchByCondition(poke_list, search_if);//ポケモンリストを更新
		prev_search_ifs.add(search_ifs.toArray(new String[0]));//アンドゥ用に検索条件を保存
		search_ifs.add(search_if);//検索条件を追加
	}
}
