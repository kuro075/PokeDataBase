package kuro075.poke.pokedatabase;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import kuro075.poke.pokedatabase.data_base.BasicData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.poke.viewable_informations.ViewableInformations;
import kuro075.poke.pokedatabase.menu.book.BookMenuActivity;
import kuro075.poke.pokedatabase.util.Utility;

public class SearchResultActivity extends BookMenuActivity{
	private static final String PACKAGE="kuro075.poke.pokedatabase";
	private static final String TAG="SearchResultActivity";
	protected static final String KEY_SEARCH_IF=PACKAGE+"."+TAG+".search_if",
								  KEY_TITLE=PACKAGE+"."+TAG+".title";

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
	private int index_sort;//ソートのインデックス
	private boolean flag_reverse=false;//逆順にソートするかどうか
	
	/* 複数選択 */
	private ListChoiceModes choice_mode=ListChoiceModes.SINGLE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utility.log(TAG, "onCreate");
		setContentView(R.layout.search_result_layout);
		/*=================/
		/  ウィジェットの登録  /
		/=================*/
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
		list_view=(ListView)findViewById(R.id.list_view);
		/*======================/
		/  インスタンス変数初期設定  /
		/======================*/
		index_viewable_informations=0;
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			title=extras.getString(KEY_TITLE);//タイトル
			final String[] ifs=extras.getStringArray(KEY_SEARCH_IF);//条件
			for(String _if:ifs){
				addSearchIf(_if);
			}
		}
		refreshListView(title);
	}
	
	/**
	 * 全てのデータ配列を取得
	 * サブクラスでオーバーライド
	 * @return
	 */
	protected BasicData[] getAllDatas(){
		return PokeDataManager.INSTANCE.getAllPokeData();
	}
	
	/**
	 * 検索条件を適用させる
	 */
	private void updateDatas(){
		BasicData[] init=getAllDatas();
		for(String search_if:search_ifs){
			
		}
	}
	protected BasicData[] searchBy(BasicData[] data_array,String search_if){
		return kuro075.poke.pokedatabase.data_base.search.poke.SearchableInformations.searchBySearchIf(data_array, search_if);
	}
	/**
	 * 検索条件を追加
	 * @param search_if
	 */
	private void addSearchIf(String search_if){
		Utility.log(TAG, "addSearchIf");
		prev_search_ifs.add(search_ifs.toArray(new String[0]));//Undo用に保存
		search_ifs.add(search_if);//検索条件を追加
	}
	
}
