package backtrack;

import java.math.BigInteger;

public class Backtrack {
public int min_diff;
	

// function that tries every possible solution
// by calling itself recursively
void BacktrackUtil(int array[], int length, boolean curr_elements[],
           int no_of_selected_elements, boolean soln[],
           int sum, int curr_sum, int curr_position)
{
    // checks whether the it is going out of bound
    if (curr_position == length)
        return;

    // checks that the numbers of elements left 
    // are not less than the number of elements
    // required to form the solution
    if ((length / 2 - no_of_selected_elements) >
            (length - curr_position))
        return;

    // consider the cases when current element 
    // is not included in the solution
    BacktrackUtil(array, length, curr_elements, 
           no_of_selected_elements, soln, sum,
           curr_sum, curr_position+1);

    // add the current element to the solution
    no_of_selected_elements++;
    curr_sum = curr_sum + array[curr_position];
    curr_elements[curr_position] = true;

    // checks if a solution is formed
    if (no_of_selected_elements == length / 2)
    {
        // checks if the solution formed is
        // better than the best solution so
        // far
        if (Math.abs(sum / 2 - curr_sum) <
                              min_diff)
        {
            min_diff = Math.abs(sum / 2 -
                              curr_sum);
            for (int i = 0; i < length; i++)
                soln[i] = curr_elements[i];
        }
    }
    else
    {
        // consider the cases where current 
        // element is included in the 
        // solution
        BacktrackUtil(array, length, curr_elements, 
                no_of_selected_elements, 
                soln, sum, curr_sum, 
                curr_position + 1);
    }

    // removes current element before 
    // returning to the caller of this
    // function
    curr_elements[curr_position] = false;
}
	// main function that generate an arr
	public void BackTrack(int array[])
	{
		System.out.println("******** Backtracking started ********");
		int length = array.length; 
		int sum1=0,sum2=0;

		// the boolen array that contains the 
		// inclusion and exclusion of an element
		// in current set. The number excluded
		// automatically form the other set
		boolean[] curr_elements = new boolean[length];
		
		// The inclusion/exclusion array for 
		// final solution
		boolean[] soln = new boolean[length];

		min_diff = Integer.MAX_VALUE;

	int sum =0;
		for (int i = 0; i < length; i++)
		{
			sum+=array[i];
			curr_elements[i] = soln[i] = false;
		}

		// Find the solution using recursive 
		// function TOWUtil()
		BacktrackUtil(array, length, curr_elements, 0, 
				soln, sum, 0, 0);

		// Print the solution
		System.out.print("The first sublist is: ");
		for (int i = 0; i < length; i++)
		{
			if (soln[i] == true)
			{
				System.out.print(array[i] + " ");
			sum1+=array[i];
			}
		}
		System.out.print("\nThe second sublist is: ");
		for (int i = 0; i < length; i++)
		{
			if (soln[i] == false)
			{
				System.out.print(array[i] + " ");
			sum2+=array[i];
			}
		}
		System.out.println("\nThe sum of first subset is: "+sum1);
		System.out.println("The sum of second subset is: "+sum2);
		System.out.println("The difference of two sublists is: "+Math.abs(sum1-sum2));
		System.out.println("******** Backtracking ended ********");
	}
	

}
