package globaldata;

public class GlobalData {
	private int[] arr;
	private int sum;
	
	public GlobalData(int[] a){
		arr = a;
		computeSum();
	}
	
	public int[] getArray() {
		return arr;
	}
	
	public int getArrayElement(int i) {
		return arr[i];
	}
	
	public int getSum() {
		return sum;
	}
	
	public void computeSum() {
		sum = 0;
		for (int num : arr) {
			sum += num;
		}
	}
}
