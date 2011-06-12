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
	/*========/
	/  データ  / 
	/========*/
	private final ItemData[] item_data;
	private final String[] item_name;
	private final Map<String,ItemData> name2item_map=new HashMap<String,ItemData>();
	private final int num;
	public static final ItemData NullData=new ItemData.Builder().build();
	
	private ItemDataManager(ItemData[] item_data){
		this.item_data=item_data;
		num=this.item_data.length;
		for(ItemData item:item_data){
			name2item_map.put(item.toString(), item);
		}
		item_name=Utility.changeToStringArray(item_data);
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
	 * ItemDataを全て取得
	 * @return
	 */
	public ItemData[] getAllData(){
		return item_data.clone();
	}
	/**
	 * item_nameを取得
	 * @return
	 */
	public String[] getAllItemName(){
		return item_name.clone();
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
		return name2item_map.get(name);
	}
}
