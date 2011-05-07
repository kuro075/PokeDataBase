package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Typeのカテゴリー
 * @author sanogenma
 *
 */
enum TypeCategories implements SearchIfCategory{
	MYSELF("タイプ"){
		@Override
		public void openDialog(Context context,final PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(TAG, "openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_spinner_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchableInformations.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,TypeData.toStringArray());
			final Spinner spinner = (Spinner)layout.findViewById(R.id.spinner);
			spinner.setAdapter(adapter);
			final Context final_context=context;
			final SearchIfListener final_listener=listener;
			final SearchTypes final_search_type=search_type;
			builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Utility.log(toString(), "onClickSearchButton");
					final_listener.receiveSearchIf(SearchableInformations.createSearchIf(MYSELF,spinner.getSelectedItem().toString(),final_search_type));
					dialog.dismiss();
				}
			});
			builder.setNeutralButton("切替", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					RELATION.openDialog(final_context,poke_array, final_search_type, final_listener);
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					final_listener.receiveSearchIf(null);
				}
			});
			builder.create().show();
		}

		/**
		 * 完成
		 */
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String type) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				Utility.log(TAG, toString()+".search");
				List<PokeData> searched_list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(poke.hasType(TypeData.fromString(type))){
						searched_list.add(poke);
					}
				}
				return searched_list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}
	},
	RELATION("タイプ相性"){
		private final String[] RELATION_KINDS = {"弱点","半減以下","無効","1/4倍","1/2倍","等倍","2倍","4倍"};
		@Override
		public void openDialog(Context context,final PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(toString(), "openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.type_relation_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchableInformations.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			//タイプスピナー
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,TypeData.toStringArray());
			final Spinner spinner_type = (Spinner)layout.findViewById(R.id.spinner_type);
			spinner_type.setAdapter(adapter);
			//検索条件スピナー
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,RELATION_KINDS);
			final Spinner spinner_class = (Spinner)layout.findViewById(R.id.spinner_class);
			spinner_class.setAdapter(adapter);
			final Context final_context=context;
			final SearchIfListener final_listener=listener;
			final SearchTypes final_search_type=search_type;
			builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Utility.log(toString(), "onClickSearchButton");
					final_listener.receiveSearchIf(SearchableInformations.createSearchIf(RELATION,createCase(spinner_type.getSelectedItem().toString(),spinner_class.getSelectedItem().toString()),final_search_type));
					dialog.dismiss();
				}
			});
			builder.setNeutralButton("切替", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					MYSELF.openDialog(final_context,poke_array, final_search_type, final_listener);
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					final_listener.receiveSearchIf(null);
				}
			});
			builder.create().show();
		}
		
		/**
		 * 検索条件(_case)を作成
		 * @param type
		 * @param relation
		 * @return
		 */
		private String createCase(String type,String relation){
			StringBuilder sb=new StringBuilder();
			sb.append(type);
			sb.append("が");
			sb.append(relation);
			return new String(sb);
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String type_and_relation) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				String[] _case=type_and_relation.split("が");
				TypeData type=TypeData.fromString(_case[0]);
				
				List<PokeData> list=new ArrayList<PokeData>();
				for(int i=0,n=RELATION_KINDS.length;i<n;i++){
					if(_case[1].equals(RELATION_KINDS[i])){
						switch(i){
							case 0://弱点
								for(PokeData poke:poke_array){
									if(type.attackTo(poke).getRelation()>100){
										list.add(poke);
									}
								}
								break;
							case 1://半減以下
								for(PokeData poke:poke_array){
									if(type.attackTo(poke).getRelation()<100){
										list.add(poke);
									}
								}
								break;
							case 2://x0
							case 3://x1/4
							case 4://x1/2
							case 5://x1
							case 6://x2
							case 7://x4
								for(PokeData poke:poke_array){
									if(type.attackTo(poke)==TypeDataManager.TypeRelations.fromIndex(i-2)){
										list.add(poke);
									}
								}
								break;
						}
						break;
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

	};
	private static final String TAG="TypeCategories";
	private final String name;
	TypeCategories(String name){
		this.name=name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	/**
	 * 文字列から取得
	 * @param name
	 * @return
	 */
	public static TypeCategories fromString(String name){
		for(TypeCategories category:values()){
			if(category.toString().equals(name)){
				return category;
			}
		}
		return null;
	}

}
