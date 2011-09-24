package kuro075.poke.pokedatabase.data_base.search.skill;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.search.SearchIf;
import kuro075.poke.pokedatabase.data_base.search.SkillSearchIfCategory;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;

enum TypeCategories implements SkillSearchIfCategory{
	MYSELF("タイプ"){
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String type) {
			// TODO 検索
			if(toString().equals(category)){
				Utility.log(TAG, toString()+".search");
				List<SkillData> searched_list=new ArrayList<SkillData>();
				for(SkillData skill:skill_array){
					if(skill.getType().equals(TypeData.fromString(type))){
						searched_list.add(skill);
					}
				}
				return searched_list.toArray(new SkillData[0]);
			}
			return new SkillData[0];
		}

		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			Utility.log(TAG, "openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.simple_spinner_dialog, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
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
					Utility.log(toString(), "onClickSearchButton");
					final_listener.receiveSearchIf(SearchIf.createSearchIf(MYSELF,spinner.getSelectedItem().toString(),final_search_type));
					dialog.dismiss();
				}
			});
			builder.setNeutralButton("切替", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					RELATION.openDialog(final_context,final_search_type, final_listener);
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final_listener.receiveSearchIf(null);
				}
			});
			builder.create().show();
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}

		
	},
	RELATION("タイプ相性"){
		private final String[] RELATION_KINDS = {"ばつぐん","ふつう","いまひとつ","なし"};
		
		/**
		 * 検索条件(_case)を作成
		 * @param type
		 * @param relation
		 * @return
		 */
		private String createCase(String type,String relation){
			StringBuilder sb=new StringBuilder();
			sb.append(type);
			sb.append("に効果");
			sb.append(relation);
			return new String(sb);
		}
		
		@Override
		public SkillData[] search(SkillData[] skill_array, String category,
				String type_and_relation) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				String[] _case=type_and_relation.split("に効果");
				TypeData type=TypeData.fromString(_case[0]);
				
				List<SkillData> list=new ArrayList<SkillData>();
				for(int i=0,n=RELATION_KINDS.length;i<n;i++){
					if(_case[1].equals(RELATION_KINDS[i])){
						switch(i){
							case 0://ばつぐん
								for(SkillData skill:skill_array){
									if(type.attackedBy(skill.getType()).getRelation()==200){
										list.add(skill);
									}
								}
								break;
							case 1://ふつう
								for(SkillData skill:skill_array){
									if(type.attackedBy(skill.getType()).getRelation()==100){
										list.add(skill);
									}
								}
								break;
							case 2://いまひとつ
								for(SkillData skill:skill_array){
									if(type.attackedBy(skill.getType()).getRelation()==50){
										list.add(skill);
									}
								}
								break;
							case 3://なし
								for(SkillData skill:skill_array){
									if(type.attackedBy(skill.getType()).getRelation()==0){
										list.add(skill);
									}
								}
								break;
						}
						break;
					}
				}
				return list.toArray(new SkillData[0]);
			}
			return new SkillData[0];
		}
		
		@Override
		public void openDialog(Context context, SearchTypes search_type,
				SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(toString(), "openDialog");
			AlertDialog.Builder builder;
			LayoutInflater factory=LayoutInflater.from(context);
			
			final View layout = factory.inflate(R.layout.type_relation_dialog_for_skill, null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchIf.createDialogTitle(this, search_type));
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
					Utility.log(toString(), "onClickSearchButton");
					final_listener.receiveSearchIf(SearchIf.createSearchIf(RELATION,createCase(spinner_type.getSelectedItem().toString(),spinner_class.getSelectedItem().toString()),final_search_type));
					dialog.dismiss();
				}
			});
			builder.setNeutralButton("切替", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					MYSELF.openDialog(final_context,final_search_type, final_listener);
				}
			});
			builder.setNegativeButton("戻る",new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final_listener.receiveSearchIf(null);
				}
			});
			builder.create().show();
		}

		@Override
		public String getCaseByFreeWord(String free_word) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};

	private static final String TAG="TypeCategories";
	private final String name;
	TypeCategories(String name){
		this.name=name;
	}
	@Override
	public String toString() {
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
	/**
	 * 検索条件文字列の種類が一致するかどうか
	 * @param category
	 * @return
	 */
	public boolean isCategory(String category){
		return toString().equals(category);
	}
}
