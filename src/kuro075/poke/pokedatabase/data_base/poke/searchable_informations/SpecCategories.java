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
	H("種族値 HP"){
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
	A("種族値 攻撃"){
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
	B("種族値 防御"){

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
	C("種族値 特攻"){

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
	D("種族値 特防"){

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
	S("種族値 素早"){
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
	TOTAL("種族値 合計"){

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
	COMPARE("種族値 比較"){

		@Override
		public PokeData[] search(PokeData[] poke_array, String category,
				String _case) {
			// TODO Auto-generated method stub
			if(toString().equals(category)){
				String[] status_option_status=_case.split(" ");
				Statuses left=Statuses.fromString(status_option_status[0]),
						 right=Statuses.fromString(status_option_status[2]);
				TwoCompareOptions center=TwoCompareOptions.fromString(status_option_status[1]);
				List<PokeData> list=new ArrayList<PokeData>();
				for(PokeData poke:poke_array){
					if(center.compareOf(poke.getSpec(left), poke.getSpec(right))){
						list.add(poke);
					}
				}
				return list.toArray(new PokeData[0]);
			}
			return new PokeData[0];
		}

		/**
		 * スピナーが三つ並んだダイアログ
		 */
		@Override
		public void openDialog(final Context context, final PokeData[] poke_array,
				final SearchTypes search_type, final SearchIfListener listener) {
			// TODO Auto-generated method stub
			Utility.log(TAG, "createIntInputDialogBuilder");
			AlertDialog.Builder builder;
			
			LayoutInflater factory=LayoutInflater.from(context);
			final View layout = factory.inflate(R.layout.three_spinner_dialog,null);
			builder = new AlertDialog.Builder(context);
			builder.setTitle(SearchableInformations.createDialogTitle(this, search_type));
			builder.setView(layout);
			final EditText edit=(EditText)layout.findViewById(R.id.edit);
			
			//左右のスピナー
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(Statuses.values()));
			final Spinner spinner_left = (Spinner)layout.findViewById(R.id.spinner_left);
			spinner_left.setAdapter(adapter);
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(Statuses.values()));
			final Spinner spinner_right = (Spinner)layout.findViewById(R.id.spinner_right);
			spinner_right.setAdapter(adapter);
			
			//真ん中のスピナー
			adapter = new ArrayAdapter<String>(context,R.layout.center_spinner_item,Utility.changeToStringArray(TwoCompareOptions.values()));
			final Spinner spinner_center = (Spinner)layout.findViewById(R.id.spinner_center);
			spinner_center.setAdapter(adapter);
			
			builder.setPositiveButton("検索", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					final int left=spinner_left.getSelectedItemPosition(),
							  center=spinner_center.getSelectedItemPosition(),
							  right=spinner_right.getSelectedItemPosition();
					if(left!=right){
						StringBuilder sb=new StringBuilder();
						sb.append(Statuses.values()[left].toString());
						sb.append(" ");
						sb.append(TwoCompareOptions.values()[center].toString());
						sb.append(" ");
						sb.append(Statuses.values()[right]);
						listener.receiveSearchIf(SearchableInformations.createSearchIf(COMPARE, new String(sb), search_type));
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
			builder.create().show();
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
				try{
					int input=Integer.valueOf(edit.getText().toString());
					if(min<=input && input<=max){
						StringBuilder sb=new StringBuilder();
						sb.append(input);
						sb.append(" ");
						sb.append(OneCompareOptions.fromIndex(spinner.getSelectedItemPosition()).toString());
						listener.receiveSearchIf(SearchableInformations.createSearchIf(category, new String(sb), search_type));
					}else{
						Utility.popToast(context,"入力値が不正です");
					}
				}catch(NumberFormatException e){
					Utility.popToast(context,"入力がありません");
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
		String[] spec_option=_case.split(" ");
		int spec=Integer.valueOf(spec_option[0]);
		OneCompareOptions option=OneCompareOptions.fromString(spec_option[1]);
		
		List<PokeData> list=new ArrayList<PokeData>();
		for(PokeData poke:poke_array){
			if(option.compareOf(poke.getSpec(index), spec)){
				list.add(poke);
			}
		}
		return list.toArray(new PokeData[0]);
	}
}
