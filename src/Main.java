import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Integer> items = new ArrayList<Integer>();
    static int binCapacity = 10;

    static long start, end;

    static List<List<Integer>> bins = new ArrayList<List<Integer>>();

    public static void betterFit(final int nextObject)
    {


        int theObject = nextObject;


        for (int i = 0; i < bins.size(); i++)
        {
            List<Integer> bin = bins.get(i);

            //Put this packet in this bin if this packet is the better fit
            int index = isBetterFit(bin, theObject);
            if (index != -1)
            {
                int tmp = theObject;
                theObject = bin.get(index);
                bin.remove(index);
                bin.add(tmp);
            }
            //return the object to be removed
        }

        bestFit(theObject);

    }

    private static int isBetterFit(final List<Integer> bin, final int packet)
    {
        List<Integer> b = bin;
        int binSize = b.stream().mapToInt(Integer::valueOf).sum();

        for (int index = 0; index < bin.size(); index++)
        {
            int newSize = 0;

            for (int index2 = 0; index2 < bin.size(); index2++)
            {
                if (index == index2)
                {
                    newSize += packet;
                }
                else
                {
                    newSize += bin.get(index2);
                }
            }

            if ((binSize < newSize) && (newSize <= binCapacity)) return index;
        }

        return -1;
    }

    public static void bestFit(int newObject)
    {
        int itemAdded = 0;

        if (!bins.isEmpty())
        {
            for (int i = 0; i < bins.size(); i++)
            {
                List<Integer> bin = bins.get(i);
                int currentCapacity = bin.stream().mapToInt(Integer::valueOf).sum();
                if (overflowCheck(newObject, currentCapacity))
                {
                    bins.get(i).add(newObject);
                    itemAdded = 1;

                    break;
                }
            }
        }

        if (itemAdded == 0)
        {
            List<Integer> newBin = new ArrayList<Integer>();
            if (newObject < binCapacity)
            {
                newBin.add(newObject);
                bins.add(newBin);
                //System.out.println("Item added 2nd if, the Bin number is : " + Bins.size());

            }
            else
            {
                System.out.println("Error !, Item size greater than bigger bin size");
            }
        }
    }

    public static boolean overflowCheck(int packet, int currentCapacity) {
        if ((packet + currentCapacity) <= binCapacity)
            return true;
        else
            return false;
    }



    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/BPP.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("'")) {
                start = System.currentTimeMillis();
                System.out.println(("Problem: " + line));
                LoadData(reader);

                for (int item : items)
                    betterFit(item);

                System.out.println("Bins:");
                for (int i = 0; i < bins.size(); i++) {
                    System.out.println("Bin " + (i + 1) + ": " + bins.get(i));

                }
                System.out.println("Total bins used: " + bins.size());

                end = System.currentTimeMillis();
                System.out.println("Time taken: " + (end - start) / 1000.0f + "s\n\n");

                bins.clear();
            }
        }

    }

    public static long LoadData(BufferedReader reader) throws IOException {

        int total_accumulated_weight = 0;

        int totalItems = 0;

        items.clear();
        // Read item weights and quantities



            // Read the problem name
            //problemName = reader.readLine().trim();
            // Read the line "number m of different item weights"
            int number_items = Integer.parseInt(reader.readLine().trim());
            // Read the capacity of the bins
            binCapacity = Integer.parseInt(reader.readLine().trim());

            int i = 0;
            int j = 0;
            while (i < number_items) {

                String[] itemLine = reader.readLine().trim().split("\\s+");
                int weight = Integer.parseInt(itemLine[0]); // Parse weight
                //System.out.println(weight);
                int quantity = Integer.parseInt(itemLine[1]); // Parse quantity

                totalItems += quantity;
                //System.out.println(quantity);
                for (int q = 0; q < quantity; q++) {
                    items.add(weight);
                    total_accumulated_weight += items.get(j + q);

                }


                j = j + quantity;
                i++;


            }



            System.out.println("capacity: " + binCapacity + ", numberItems: " + number_items + ", total items: " + totalItems + ", total weights: " + total_accumulated_weight);
            System.out.println(items);



        return 0;
    }
}
