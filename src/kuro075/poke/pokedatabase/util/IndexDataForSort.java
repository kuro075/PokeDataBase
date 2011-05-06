package kuro075.poke.pokedatabase.util;


/**
 * ソートに使うデータクラス
 * @author sanogenma
 *
 */
public class IndexDataForSort implements Comparable<IndexDataForSort>{

	int data=-1;
	int index=-1;
	
	public IndexDataForSort(int index,int data){
		this.index=index;
		this.data=data;
	}

	public int getIndex(){
		return index;
	}
	public int getData(){
		return data;
	}
	
	@Override
	public int compareTo(IndexDataForSort another) {
		// TODO Auto-generated method stub
		return data-another.getData();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndexDataForSort other = (IndexDataForSort) obj;
		return data==other.getData();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		return result;
	}

}
