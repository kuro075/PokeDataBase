package kuro075.poke.pokedatabase.data_base.poke.searchable_informations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.SearchIfListener;
import kuro075.poke.pokedatabase.data_base.SearchTypes;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeData.Statuses;
import kuro075.poke.pokedatabase.data_base.poke.viewable_informations.ViewableInformations;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public enum SpecCategories implements SearchIfCategory{
	H("HP種族値"){
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,Statuses.H.getIndex());
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_HP.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(Statuses.H), poke_array[poke_array.length-1].getSpec(Statuses.H)).create().show();
		}
	},
	A("攻撃種族値"){
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,Statuses.A.getIndex());
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_ATTACK.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(Statuses.A), poke_array[poke_array.length-1].getSpec(Statuses.A)).create().show();
		}
	},
	B("防御種族値"){

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,Statuses.B.getIndex());
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_BLOCK.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(Statuses.B), poke_array[poke_array.length-1].getSpec(Statuses.B)).create().show();
			
		}
	},
	C("特攻種族値"){

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,Statuses.C.getIndex());
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_CONTACT.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(Statuses.C), poke_array[poke_array.length-1].getSpec(Statuses.C)).create().show();
			
		}
	},
	D("特防種族値"){

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,Statuses.D.getIndex());
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_DEFENSE.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(Statuses.D), poke_array[poke_array.length-1].getSpec(Statuses.D)).create().show();
		}
	},
	S("素早種族値"){
		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,Statuses.S.getIndex());
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_SPEED.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(Statuses.S), poke_array[poke_array.length-1].getSpec(Statuses.S)).create().show();
		}
	},
	TOTAL("種族値合計"){

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				return searchWithOneSpec(poke_array,_case,-1);
			}
			return new PokeData[0];
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			Arrays.sort(poke_array,ViewableInformations.SPEC_TOTAL.getComparator());
			createIntInputDialogBuilder(context, poke_array, search_type, listener, this, poke_array[0].getSpec(6), poke_array[poke_array.length-1].getSpec(6)).create().show();
		}
	},
	COMPARE("種族値比較"){

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void openDialog(Context context, PokeData[] poke_array,
				SearchTypes search_type, SearchIfListener listener) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private static final String TAG="SpecCategories";
	private final String name;
	SpecCategories(String name){this.name=name;}
	@Override
	public String toString(){return name;}
	
	/**
	 * 数値をひとつ入力して比較を行うダイアログ
	 * @param context
	 * @param search_type
	 * @param listener
	 * @param category
	 * @param min
	 * @param max
	 * @return
	 */
	public static AlertDialog.Builder createIntInputDialogBuilder(final Context context,final PokeData[] poke_array,final SearchTypes search_type,final SearchIfListener listener,final SearchIfCategory category,final int min,final int max){
		Utility.log(TAG, "createIntInputDialogBuilder");
		Log.v(TAG,"openSpecInputDialog");
		AlertDialog.Builder builder;
		
		LayoutInflater factory=LayoutInflater.from(context);
		final View layout = factory.inflate(R.layout.int_input_dialog,null);
		builder = new AlertDialog.Builder(context);
		builder.setTitle(SearchableInformations.createDialogTitle(category, search_type));
		builder.setView(layout);
		final EditText edit=(EditText)layout.findViewById(R.id.edit);
		edit.setHint(min+"~"+max);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(OneCompareOptions.values()));
		final Spinner spinner = (Spinner)layout.findViewById(R.id.spinner);
		spinner.setAdapter(adapter);
		builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String input=edit.getText().toString();
				if(input.equals("")){
					Utility.popToast(context,"入力がありません");
				}else{
					listener.receiveSearchIf(SearchableInformations.createSearchIf(category, input+OneCompareOptions.fromIndex(spinner.getSelectedItemPosition()), search_type));
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("戻る", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				SearchableInformations.SPEC.openDialog(context,poke_array, search_type, listener);
			}
		});
		return builder;
	}
	
	/**
	 * 文字列からSpecCategoriesを取得
	 * @param name
	 * @return
	 */
	public static SpecCategories fromString(String name){
		for(SpecCategories spec:values()){
			if(spec.toString().equals(name)) return spec;
		}
		return null;
	}
	
	/**
	 * ステータス１種類での検索
	 * @param poke_array
	 * @param _case
	 * @param status
	 * @return
	 */
	public static PokeData[] searchWithOneSpec(PokeData[] poke_array,String _case,int index){
		int spec=0;
		StringBuilder option_builder=new StringBuilder();
		for(int i=0,n=_case.length();i<n;i++){
			final char tmp=_case.charAt(i);
			if('0'<=tmp && tmp<='9'){
				spec=spec*10+(tmp-'0');
			}else{
				option_builder.append(tmp);
			}
		}
		OneCompareOptions option=OneCompareOptions.fromString(new String(option_builder));
		
		List<PokeData> list=new ArrayList<PokeData>();
		for(PokeData poke:poke_array){
			if(option.compareOf(poke.getSpec(index), spec)){
				list.add(poke);
			}
		}
		return list.toArray(new PokeData[0]);
	}
}
