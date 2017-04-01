package tools;
import java.util.Comparator;

import Model.SingleResult;

public class NGramComparator implements Comparator<SingleResult>
{
    @Override
    public int compare(SingleResult x, SingleResult y)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        /*if (x.score > y.score)
        {
            return -1;
        }
        if (x.score < y.score)
        {
            return 1;
        }
        return 0;*/
    	return  x.score -y.score;
    }
}