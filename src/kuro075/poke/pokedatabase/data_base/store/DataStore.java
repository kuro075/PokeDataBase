package kuro075.poke.pokedatabase.data_base.store;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.store.history.PageHistoryData;
import kuro075.poke.pokedatabase.data_base.store.history.SearchHistoryData;
import kuro075.poke.pokedatabase.util.Utility;

public class DataStore {
	public enum DataTypes{
		POKEMON("poke") {
			@Override
			void startSearchResultActivity(Context context, String title,
					String[] search_ifs) {
				// TODO Auto-generated method stub
				kuro075.poke.pokedatabase.poke_book.PokeSearchResultActivity.startThisActivity(context,title,search_ifs);
			}

			@Override
			void startPageActivity(Context context, String name) {
				// TODO Auto-generated method stub
				kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity.startThisActivity(context, name);
			}
		},
		SKILL("skill"){
			@Override
			void startSearchResultActivity(Context context, String title,
					String[] search_ifs) {
				// TODO Auto-generated method stub
				
			}

			@Override
			void startPageActivity(Context context, String name) {
				// TODO Auto-generated method stub
				
			}
		};
		private final String name;
		private final DataStore history_store,star_store;
		DataTypes(String name){
			this.name=name;
			this.history_store=new DataStore(name+"_history");
			this.star_store=new DataStore(name+"_star");
		}
		/**
		 * 履歴データストアクラスを取得
		 * @return
		 */
		public DataStore getHistoryStore(){
			return history_store;
		}
		/**
		 * お気に入りデータストアクラスを取得
		 * @return
		 */
		public DataStore getStarStore(){
			return star_store;
		}
		@Override
		public String toString(){
			return name;
		}
	
		/**
		 * お気に入り登録ダイアログ
		 * Title:nameをお気に入りに登録しますか？
		 * 登録 or Cancel
		 * @param context
		 * @param name 登録する名前
		 */
		public void openSaveStarDialog(final Context context,final String name){
			if(star_store.indexPageOf(name)>=0){
				Utility.popToast(context, "既に登録されています");
			}else{
				AlertDialog.Builder builder;
				//final View layout = factory.inflate(R.layout.simple_dialog, null);
				builder = new AlertDialog.Builder(context);
				StringBuilder sb=new StringBuilder();
				sb.append("「");
				sb.append(name);
				sb.append("」");
				sb.append("をお気に入りに登録しますか？");
				builder.setTitle(new String(sb));
				//builder.setView(layout);
				builder.setPositiveButton("登録",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						getStarStore().addPageData(name);
						StringBuilder sb=new StringBuilder();
						sb.append("「");
						sb.append(name);
						sb.append("」");
						sb.append("をお気に入りに登録しました");
						Utility.popToast(context, new String(sb));
					}
				});
				builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		}
		
		/**
		 * お気に入り登録ダイアログを開く
		 * Title:お気に入りに登録しますか？
		 * 登録 or Cancel
		 * @param context
		 * @param names 登録する名前配列
		 */
		public void openSaveStarDialog(final Context context,final String[] names){
			StringBuilder sb=new StringBuilder();
			for(int i=0,n=names.length;i<n;i++){
				sb.append(names[i]);
				if(i!=n-1){
					sb.append("・");
				}
			}
			Utility.openSimpleTextDialog(context,"お気に入りに登録しますか？", new String(sb), 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						getStarStore().addPageData(names);
						StringBuilder sb=new StringBuilder();
						sb.append("「");
						for(int i=0,n=names.length;i<n;i++){
							sb.append(names[i]);
							if(i!=n-1){
								sb.append("・");
							}
						}
						sb.append("」をお気に入りに登録しました");
						Utility.popToast(context, new String(sb));
					}
				}
			);
		}
		/**
		 * ショートカット登録ダイアログ
		 * @param context
		 * @param search_ifs
		 */
		public void openSaveShortCutDialog(final Context context,final String[] search_ifs){
			Utility.log(TAG, "openSaveSearchIfDialog");
			if(star_store.haveSearch(search_ifs)){
				Utility.popToast(context, "既に登録されています");
			}else{
				AlertDialog.Builder builder=new AlertDialog.Builder(context);
				LayoutInflater factory=LayoutInflater.from(context);
				
				final View layout = factory.inflate(R.layout.simple_edit_dialog, null);
				builder = new AlertDialog.Builder(context);
				builder.setTitle("タイトルを入力してください");
				builder.setView(layout);
				final EditText edit_text=((EditText)layout.findViewById(R.id.edit));
				edit_text.setHint("ショートカット");
				builder.setPositiveButton("登録",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String title=edit_text.getText().toString();
						if(title.equals("")){
							title=edit_text.getHint().toString();
						}
						getStarStore().addSearchData(title, search_ifs);
						StringBuilder sb=new StringBuilder();
						sb.append("「");
						sb.append(title);
						sb.append("」");
						sb.append("をショートカットに登録しました");
						Utility.popToast(context, new String(sb));
					}
				});
				
				builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		}
		
		/*======/
		/  履歴  /
		/======*/
		/**
		 * 履歴ダイアログを開く
		 * @param context
		 */
		public void openHistoryDialog(final Context context){
			Utility.log(TAG, "openHistoryDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle("履歴");
			builder.setView(layout);
			
			final PageHistoryData[] histories=this.getHistoryStore().getPageDataList();
			Utility.reverseArray(histories);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(histories));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			//閉じるボタン
			builder.setPositiveButton("閉じる",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			final AlertDialog dialog=builder.create();
			//リストを選択した時の動作
			//ページアクティビティーを表示
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Utility.log(TAG,"onItemClick");
					startPageActivity(context,histories[position].toString());
					dialog.dismiss();
				}
			});
			//リストを長押しした時の動作
			//削除確認ダイアログを表示して削除
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					StringBuilder sb=new StringBuilder();
					sb.append("「");
					sb.append(histories[position]);
					sb.append("」");
					sb.append("を履歴から削除しますか？");
					
					Utility.openCheckDialog(context, new String(sb),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								getHistoryStore().removePageData(histories[position]);
								StringBuilder sb=new StringBuilder();
								sb.append("「");
								sb.append(histories[position]);
								sb.append("」を履歴から削除しました");
								Utility.popToast(context, new String(sb));
								openHistoryDialog(context);
								dialog.dismiss();
							}
						},
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								openHistoryDialog(context);
								dialog.dismiss();
							}
						}
					);
					return true;
				}
			});
			dialog.show();
		}
		
		
		/*===========/
		/  お気に入り  /
		/===========*/
		/**
		 * お気に入りダイアログを開く
		 * @param context
		 */
		public void openStarDialog(final Context context){
			Utility.log(TAG, "openStarDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle("お気に入り");
			builder.setView(layout);
			
			final PageHistoryData[] stars=this.getStarStore().getPageDataList();
			Arrays.sort(stars);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(stars));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			//閉じるボタン
			builder.setPositiveButton("閉じる",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			final AlertDialog dialog=builder.create();
			//リストを選択した時の動作
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					Utility.log(TAG,"onItemClick");
					startPageActivity(context, stars[position].toString());
					dialog.dismiss();
				}
			});
			//リストを長押しした時の動作
			//お気に入り編集ダイアログを開く
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub
					Utility.openSimpleTextDialog(context, "お気に入りから削除しますか？", stars[position].getName(), 
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									getStarStore().removePageData(stars[position]);
									StringBuilder sb=new StringBuilder();
									sb.append("「");
									sb.append(stars[position].getName());
									sb.append("」をお気に入りから削除しました");
									Utility.popToast(context, new String(sb));
									openStarDialog(context);
									dialog.dismiss();
								}
							},
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									openStarDialog(context);
									dialog.dismiss();
								}
							}
					);
					dialog.dismiss();
					return true;
				}
			});
			dialog.show();
		}
		
		
		/*==============/
		/  ショートカット  /
		/==============*/
		/**
		 * ショートカットダイアログを開く
		 * @param context
		 */
		public void openShortCutDialog(final Context context){
			Utility.log(TAG, "openShortCutDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle("ショートカット");
			builder.setView(layout);
			
			final SearchHistoryData[] short_cuts=this.getStarStore().getSearchDataList();
			Arrays.sort(short_cuts);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,Utility.changeToStringArray(short_cuts));
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			//閉じるボタン
			builder.setPositiveButton("閉じる",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			final AlertDialog dialog=builder.create();
			//リストを選択した時の動作
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					Utility.log(TAG,"onItemClick");
					startSearchResultActivity(context, short_cuts[position].getTitle(), short_cuts[position].getSearchIfs());
					dialog.dismiss();
				}
			});
			//リストを長押しした時の動作
			//詳細を表示して「表示・削除」ボタンを表示する
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					openSearchIfDetailViewDialog(context,short_cuts[position]);
					return true;
				}
			});
			dialog.show();
		}
		/**
		 * 検索条件詳細表示ダイアログ(←ShortCutDialogの長押しから
		 * 「編集・削除・戻る」ボタン
		 * @param context
		 * @param search
		 */
		private void openSearchIfDetailViewDialog(final Context context,final SearchHistoryData search){
			Utility.log(TAG, "openShortCutDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.detail_if_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(search.getTitle());
			builder.setView(layout);
			
			final LinearLayout linearlayout=(LinearLayout)layout.findViewById(R.id.layout);
			for(String _if:search.getSearchIfs()){
				TextView tv=new TextView(linearlayout.getContext());
				tv.setText(_if);
				tv.setTextSize(17.0f);
				linearlayout.addView(tv);
			}
			/*
			//表示ボタン(検索結果アクティビティーに
			builder.setPositiveButton("表示", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					startSearchResultActivity(context,search.getTitle(),search.getSearchIfs());
					dialog.dismiss();
				}
			});*/
			
			//編集ボタン
			builder.setPositiveButton("編集", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					openEditShortCutTitleDialog(context,search);
					dialog.dismiss();
				}
			});
			//削除ボタン
			//ショートカット削除確認ダイアログを開く
			builder.setNeutralButton("削除", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Utility.openSimpleTextDialog(context, "ショートカットから削除しますか？", search.getTitle(), 
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									getStarStore().removeSearchData(search);
									StringBuilder sb=new StringBuilder();
									sb.append("「");
									sb.append(search.getTitle());
									sb.append("」をショートカットから削除しました");
									Utility.popToast(context, new String(sb));
									openShortCutDialog(context);
									dialog.dismiss();
								}
							},
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									openSearchIfDetailViewDialog(context,search);
									dialog.dismiss();
								}
							}
					);
				}
			});
			//戻るボタン
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					openShortCutDialog(context);
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		
		/**
		 * ショートカットタイトル編集ダイアログを開く（←SearchIfDetailViewDialogの編集ボタンから
		 * 「編集・削除・戻る」ボタン
		 * @param context
		 * @param search
		 */
		private void openEditShortCutTitleDialog(final Context context,final SearchHistoryData search){
			AlertDialog.Builder builder=new AlertDialog.Builder(context);
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_edit_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle("タイトルを入力してください");
			builder.setView(layout);
			final EditText edit_text=((EditText)layout.findViewById(R.id.edit));
			edit_text.setHint("ショートカット");
			edit_text.setText(search.getTitle());
			//変更ボタン
			//タイトルを変更する
			builder.setPositiveButton("変更",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String title=edit_text.getText().toString();
					if(title.equals("")){
						title=edit_text.getHint().toString();
					}
					getStarStore().addSearchData(title, search);
					StringBuilder sb=new StringBuilder();
					sb.append("タイトルを「");
					sb.append(title);
					sb.append("」");
					sb.append("に変更しました");
					Utility.popToast(context, new String(sb));
					openShortCutDialog(context);
				}
			});
			
			//戻るボタン（詳細ビューダイアログを開く
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					openSearchIfDetailViewDialog(context,search);
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		
		abstract void startSearchResultActivity(Context context,String title,String[] search_ifs);
		abstract void startPageActivity(Context context,String name);
	}

	private static final String TAG="DataStore";
	private static final String PERTITION="¥¥";
	
	private final List<PageHistoryData> page_data_list = new ArrayList<PageHistoryData>();
	
	private final List<SearchHistoryData> search_data_list = new ArrayList<SearchHistoryData>();
	private final String page_file_name,search_file_name;
	
	private DataStore(String data_name){
		StringBuilder sb=new StringBuilder();
		sb.append(TAG);
		sb.append("-");
		sb.append(data_name);
		sb.append("_page_file_name.txt");
		page_file_name=new String(sb);
		
		sb=new StringBuilder();
		sb.append(TAG);
		sb.append("-");
		sb.append(data_name);
		sb.append("_search_file_name.txt");
		search_file_name=new String(sb);
		
		readFile();
	}
	
	/**
	 * ページを追加
	 * @param poke
	 */
	public void addPageData(String name){
		int index=indexPageOf(name);
		if(index>=0){
			page_data_list.remove(index);
		}
		page_data_list.add(new PageHistoryData(page_data_list.size(),name,getDate()));
		writePageFile();
	}
	
	/**
	 * ページを複数追加
	 * @param names
	 */
	public void addPageData(String[] names){
		for(String name:names){
			int index=indexPageOf(name);
			if(index>=0){
				page_data_list.remove(index);
			}
			page_data_list.add(new PageHistoryData(page_data_list.size(),name,getDate()));
		}
		writePageFile();
	}
	/**
	 * 検索条件を追加
	 * @param search_ifs
	 */
	public void addSearchData(String title,String[] search_ifs){
		int index=indexSearchOf(search_ifs);
		if(index>=0){
			search_data_list.remove(index);
		}
		String new_title=title;
		int num=2;
		boolean find_flag=false;
		do{
			find_flag=false;
			for(SearchHistoryData search:search_data_list){
				if(search.getTitle().equals(new_title)){
					StringBuilder sb=new StringBuilder();
					sb.append(title);
					sb.append(num);
					new_title=new String(sb);
					num++;
					find_flag=true;
				}
			}
		}while(find_flag);
		search_data_list.add(new SearchHistoryData(search_data_list.size(),new_title,search_ifs));
		writeSearchFile();
	}

	/**
	 * 検索条件を新しいタイトルで追加
	 * （検索条件のタイトル変更
	 * @param new_title　新しいタイトル
	 * @param search　前のデータ
	 */
	public void addSearchData(String new_title,SearchHistoryData search){
		addSearchData(new_title,search.getSearchIfs());
	}
	/**
	 * タイトル無しで検索条件を追加(タイトルには日時が保存される
	 * @param search_ifs
	 */
	public void addSearchDataWithoutTitle(String[] search_ifs){
		addSearchData(getDate(),search_ifs);
	}
	
	/**
	 * page_data_listの要素を削除
	 * @param page
	 */
	private void removePageData(PageHistoryData page){
		page_data_list.remove(page);
		writePageFile();
	}
	
	/**
	 * search_data_listの要素を削除
	 * @param search
	 */
	private void removeSearchData(SearchHistoryData search){
		search_data_list.remove(search);
		writeSearchFile();
	}
	
	/**
	 * search_ifsを持つデータを持っているか
	 * @param search_ifs
	 * @return
	 */
	private boolean haveSearch(String[] search_ifs){
		for(SearchHistoryData data:search_data_list){
			if(data.equalsSearchIfs(search_ifs)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 名前がnameのページデータクラスのインデックスを取得
	 * @param name
	 * @return
	 */
	private int indexPageOf(String name){
		for(int i=0,n=page_data_list.size();i<n;i++){
			if(page_data_list.get(i).getName().equals(name)){
				Utility.log(TAG ,"index:"+i);
				return i;
			}
		}
		return -1;
	}
	/**
	 * 検索条件がsearch_ifsの検索データクラスのインデックスを取得
	 * @param search_ifs
	 * @return
	 */
	private int indexSearchOf(String[] search_ifs){
		for(int i=0,n=search_data_list.size();i<n;i++){
			if(search_data_list.get(i).equalsSearchIfs(search_ifs)){
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * 現在の時刻を取得
	 * @return
	 */
	private String getDate(){
		final Calendar calendar = Calendar.getInstance();
		StringBuilder sb=new StringBuilder();
		sb.append(calendar.get(Calendar.YEAR));//year
		sb.append("/");
		sb.append(calendar.get(Calendar.MONTH)+1);//month
		sb.append("/");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));//day
		sb.append(" ");
		sb.append(calendar.get(Calendar.HOUR_OF_DAY));//hour
		sb.append(":");
		sb.append(calendar.get(Calendar.MINUTE));//minute
		sb.append(":");
		sb.append(calendar.get(Calendar.SECOND));//second
		return new String(sb);
	}
	/**
	 * ページ履歴を取得
	 * @return
	 */
	public PageHistoryData[] getPageDataList(){
		return page_data_list.toArray(new PageHistoryData[0]);
	}
	/**
	 * 検索を取得
	 * @return
	 */
	public SearchHistoryData[] getSearchDataList(){
		return search_data_list.toArray(new SearchHistoryData[0]);
	}
	
	/**
	 * data_listを読み込み
	 * @param file_name
	 */
	private void readFile(){
		Utility.log(TAG,"readFile");
		BufferedReader br=null;
		String line=null;//
		FileInputStream fis = null;
		/*=================/
		/  page_data_list  /
		/=================*/
		try{
			fis=new FileInputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+page_file_name);
			br = new BufferedReader(new InputStreamReader(fis));
			while((line=br.readLine())!=null){
				String[] tmp=line.split(PERTITION);
				page_data_list.add(new PageHistoryData(page_data_list.size(),tmp[0],tmp[1]));
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br!=null)
					br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		/*===================/
		/  search_data_list  / 
		/===================*/
		try{
			fis=new FileInputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+search_file_name);
			br = new BufferedReader(new InputStreamReader(fis));
			while((line=br.readLine())!=null){
				String[] tmp=line.split(PERTITION);
				String[] searchs=new String[tmp.length-1];
				for(int i=1,n=tmp.length;i<n;i++){
					searchs[i-1]=tmp[i];
				}
				search_data_list.add(new SearchHistoryData(search_data_list.size(),tmp[0],searchs));
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br!=null)
					br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * page_data_listをファイルに書き込み
	 */
	private void writePageFile(){
		Utility.log(TAG,"writePageFile");
		BufferedWriter bw=null;
		FileOutputStream fis = null;
		/*=================/
		/  page_data_list  /
		/=================*/
		try{
			fis=new FileOutputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+page_file_name);
			bw = new BufferedWriter(new OutputStreamWriter(fis));
			for(PageHistoryData data:page_data_list){
				bw.write(data.getName());
				bw.write(PERTITION);
				bw.write(data.getDate());
				bw.write("\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(bw!=null)
					bw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * search_data_listをファイルに書き込み
	 */
	private void writeSearchFile(){
		Utility.log(TAG,"writeSearchFile");
		BufferedWriter bw=null;
		FileOutputStream fis = null;
		/*===================/
		/  search_data_list  / 
		/===================*/
		try{
			fis=new FileOutputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+search_file_name);
			bw = new BufferedWriter(new OutputStreamWriter(fis));
			for(SearchHistoryData data:search_data_list){
				bw.write(data.getTitle());
				for(String _if:data.getSearchIfs()){
					bw.write(PERTITION);
					bw.write(_if);
				}
				bw.write("\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(bw!=null)
					bw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
