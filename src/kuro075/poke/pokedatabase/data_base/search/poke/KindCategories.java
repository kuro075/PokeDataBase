package kuro075.poke.pokedatabase.data_base.search.poke;

import java.util.ArrayList;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.search.PokeSearchIfCategory;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.SearchIfCategory;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public enum KindCategories implements PokeSearchIfCategory{
	EVOLUTION("進化"){
		private String[] EVOLUTION_KINDS={"たねポケモン","1進化後","2進化後","進化可","進化系列無し","最終進化"};
		@Override
		public void openDialog(Context context,
				SearchTypes search_type,SearchIfListener listener) {
			Utility.log(toString(),"openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_list_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
			builder.setView(layout);
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_list_item,EVOLUTION_KINDS);
			final ListView listView = (ListView) layout.findViewById(R.id.list_view);
			listView.setAdapter(adapter);
			
			final SearchIfListener final_listener=listener;
			final SearchTypes final_search_type=search_type;

			builder.setPositiveButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 
					final_listener.receiveSearchIf(null);
				}
			});
			
			final AlertDialog dialog=builder.create();
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// 
					Utility.log(toString(),"onItemClick");
					final_listener.receiveSearchIf(SearchIf.createSearchIf(EVOLUTION,EVOLUTION_KINDS[position],final_search_type));
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String kind) {
			// 
			if(toString().equals(category)){
				List<PokeData> list=new ArrayList<PokeData>();
				for(int i=0,n=EVOLUTION_KINDS.length;i<n;i++){
					if(EVOLUTION_KINDS[i].equals(kind)){
						switch(i){
							case 0://"たねポケモン"
								for(PokeData poke:poke_array){
									if(poke.isSeedPokemon()) list.add(poke);
								}
								break;
							case 1://"1進化後"
								for(PokeData poke:poke_array){
									if(poke.isAfterOneEvolution()) list.add(poke);
								}
								break;
							case 2://"2進化後"
								for(PokeData poke:poke_array){
									if(poke.isAfterTwoEvolution()) list.add(poke);
								}
								break;
							case 3://"進化可"
								for(PokeData poke:poke_array){
									if(poke.isEvolutionable()) list.add(poke);
								}
								break;
							case 4://"進化系列無し
								for(PokeData poke:poke_array){
									if(poke.isNotEvolution()) list.add(poke);
								}
								break;
							case 5://最終進化
								for(PokeData poke:poke_array){
									if(poke.isFinalEvolution()) list.add(poke);
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

		/**
		 * フリーワードから検索条件(_case)を返す
		 * 該当文字列)
		 */
		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return "";
		}
	};
	private final String name;
	KindCategories(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	
	/**
	 * 文字列からKindCategoriesを取得
	 * @param name
	 * @return
	 */
	public static KindCategories fromString(String name){
		for(KindCategories kind:values()){
			if(kind.toString().equals(name)) return kind;
		}
		return null;
	}
	
	
	/**
	 * 検索条件文字列の種類が一致するかどうか
	 * @param category
	 * @return
	 */
	public boolean isCategory(String category){
		return toString().equals(category);
	}
}
