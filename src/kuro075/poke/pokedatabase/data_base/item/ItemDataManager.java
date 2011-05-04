package kuro075.poke.pokedatabase.data_base.item;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.item.ItemData.ItemClasses;
import kuro075.poke.pokedatabase.data_base.item.ItemData.ItemSubClasses;
import kuro075.poke.pokedatabase.util.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class ItemDataManager {
	private static final String TAG="ItemDataManager";
	public static final ItemDataManager INSTANCE = new Builder().build();
	
	//================================================================================
	/*==========/
	/  Builder  / 
	/==========*/
	private static class Builder{
		List<ItemData> item_list=new ArrayList<ItemData>();
		
		private Builder(){
			readFile();
		}
		private void readFile(){
			Utility.log(TAG,"readItemData");
			FileInputStream fis=null;
			BufferedReader br=null;
			String line,tmp;//
			StringTokenizer st;
			int no=0;
			
			try{
				fis = new FileInputStream(kuro075.poke.pokedatabase.util.Utility.DATAPATH+kuro075.poke.pokedatabase.util.Utility.FileNames.ITEM);
				br=new BufferedReader(new InputStreamReader(fis));
				
				while((line=br.readLine())!=null){
					st = new StringTokenizer(line);//トークンに分ける
					if(st.hasMoreTokens()){
						tmp=st.nextToken();
						if(st.hasMoreTokens()){
							ItemData.Builder item_builder=new ItemData.Builder();
							//==============================================================
							//アイテムの管理ナンバー
							Utility.log(TAG,"No."+no);
							item_builder.setNo(no);
							no++;
							//==============================================================
							//アイテムの名前
							item_builder.setName(tmp);
							//==============================================================
							//アイテムの分類
							item_builder.setItemClass(ItemClasses.fromString(st.nextToken()));
							//==============================================================
							//アイテムのサブ分類
							item_builder.setItemSubClass(ItemSubClasses.fromString(st.nextToken()));
							//==============================================================
							//アイテムの買値
							item_builder.setBuyValue(Integer.valueOf(st.nextToken()));
							//==============================================================
							//アイテムの売値
							item_builder.setSellValue(Integer.valueOf(st.nextToken()));
							//==============================================================
							//アイテムの使用時の効果
							item_builder.setUsingEffect(st.nextToken());
							//==============================================================
							//アイテムの所持時の効果
							item_builder.setHavingEffect(st.nextToken());
							//==============================================================
							//アイテムの入手場所
							item_builder.setGettingPlace(st.nextToken());

							//==============================================================
							//ItemDataをビルドして登録
							item_list.add(item_builder.build());
							
							
						}
					}
				}
			}catch(IOException e){
				Log.e(TAG,"itemdata read error");
			}finally{
				try{
					if(br!=null){
						br.close();
					}
				}catch(IOException e){
					
				}
			}
			Utility.log(TAG,"end readFile");
		}
		private ItemDataManager build(){
			return new ItemDataManager(item_list.toArray(new ItemData[0]));
		}
	}
	//================================================================================
	/*=======/
	/  enum  / 
	/=======*/
	/**
	 * 表示可能な情報
	 */
	public enum ViewableInformations{
		CLASS("分類") {
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				return item.getItemClass().toString();
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getItemClass().toString().compareTo(item2.getItemClass().toString());
					}
				};
			}
		},
		SUB_CLASS("サブ分類"){
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				return item.getItemSubClass().toString();
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getItemSubClass().toString().compareTo(item2.getItemSubClass().toString());
					}
				};
			}
		},
		BUY_VALUE("買値"){
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				final int value=item.getBuyValue();
				if(value<0){
					return "-";
				}
				return String.valueOf(value);
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getBuyValue()-item2.getBuyValue();
					}
				};
			}
		},
		SELL_VALUE("売値"){
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				final int value=item.getSellValue();
				if(value<0){
					return "-";
				}
				return String.valueOf(value);
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getSellValue()-item2.getSellValue();
					}
				};
			}
		},
		USING_EFFECT("使用時の効果"){
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				return item.getUsingEffect();
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getUsingEffect().compareTo(item2.getUsingEffect().toString());
					}
				};
			}
		},
		HAVING_EFFECT("所持時の効果"){
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				return item.getHavingEffect();
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getHavingEffect().compareTo(item2.getHavingEffect().toString());
					}
				};
			}
		},
		GETTING_PLACE("入手場所"){
			@Override
			public String getInformation(ItemData item) {
				// TODO Auto-generated method stub
				return item.getGettingPlace();
			}
			@Override
			public Comparator<ItemData> getComparator() {
				// TODO Auto-generated method stub
				return new Comparator<ItemData>(){
					@Override
					public int compare(ItemData item1, ItemData item2) {
						// TODO Auto-generated method stub
						return item1.getGettingPlace().compareTo(item2.getGettingPlace());
					}
				};
			}
		};
		
		/*========/
		/  メンバ  /
		/========*/
		private final String name;
		ViewableInformations(String name){this.name=name;}
		@Override
		public String toString(){return name;}
		/**
		 * 表示する情報を取得
		 * @param skill
		 * @return
		 */
		abstract public String getInformation(ItemData item);
		/**
		 * ソートに使うComparatorを取得
		 * @return
		 */
		abstract public Comparator<ItemData> getComparator();
	}
	/**
	 * 検索可能な情報
	 * @author sanogenma
	 *
	 */
	public enum SearchableInformations{
		NAME("名前") {
			@Override
			public AlertDialog getDialog(Context context, ItemData[] skills,
					SearchTypes search_type, SearchedItemListener listener) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ItemData[] search(ItemData[] skill_array, String condition) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		private final String name;
		SearchableInformations(String name){this.name=name;}
		@Override
		public String toString(){return name;}

		/**
		 * ダイアログを取得するメソッド
		 * @param context
		 * @param skills
		 * @param search_type
		 * @param listener
		 * @return
		 */
		abstract public AlertDialog getDialog(Context context,ItemData[] skills,SearchTypes search_type,SearchedItemListener listener);
		/**
		 * 検索条件(文字列)からポケモンを検索して返すメソッド
		 * @param condition　検索条件
		 * @return
		 */
		abstract public ItemData[] search(ItemData[] skill_array,String condition);
		/**
		 * condition=種類:hoge(SearchTypes)
		 * @param skill_array
		 * @param condtion
		 * @return
		 */
		public static ItemData[] searchByCondition(ItemData[] skill_array,String condition){
			String[] tmp=condition.split(":()");
			return fromString(tmp[0]).search(skill_array, tmp[1]);
		}
		
		private static final Map<String,SearchableInformations>
			stringToEnum = new HashMap<String,SearchableInformations>();//文字列からenumへ
		static { //定数名からenum定数へのマップを初期化
			for(SearchableInformations si : values()){
				stringToEnum.put(si.toString(), si);
			}
		}
		/**
		 * 文字列からSearchableInformationsを取得
		 * @param step
		 * @return
		 */
		public static SearchableInformations fromString(String name){
			return stringToEnum.get(name);
		}
	}
	//================================================================================
	/*========/
	/  データ  / 
	/========*/
	private final ItemData[] item_data;
	private final String[] item_name;
	private final int num;
	public static final ItemData NullData=new ItemData.Builder().build();
	
	private ItemDataManager(ItemData[] item_data){
		this.item_data=item_data;
		num=this.item_data.length;
		item_name=new String[num];
		for(int i=0;i<num;i++){
			this.item_name[i]=item_data[i].toString();
		}
	}
	//================================================================================
	/*==========/
	/  ゲッター  /
	/==========*/
	/**
	 * item_dataの数を返す
	 * @return
	 */
	public int getNum(){
		return num;
	}
	
	/**
	 * ItemDataをインデックスから取得
	 * @param index
	 * @return
	 */
	public ItemData getItemData(int index){
		if(index<0 || num<=index) return NullData;
		return item_data[index];
	}
	
	/**
	 * ItemDataを名前から取得
	 * @param name
	 * @return
	 */
	public ItemData getItemData(String name){
		final int index=Arrays.binarySearch(item_name, name);
		return getItemData(index);
	}
}
