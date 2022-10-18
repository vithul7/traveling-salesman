package com.company;
import java.util.*;

//initialize population at very start of code
//then, start while loop
//while loop has terminating condition of generations
//in while loop
//      calculate the fitness values of every tour in the population
//      call selection methods
//          selection methods should create an array of Tours, which replaces the HashMap of Tours later
//      omit crossover and mutation functions
//      increment the generation count


public class Main {

    public static void main(String[] args) {
        int generationCount = 0;
        int initialPopulationSize = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter initial population size for genetic algorithm: ");
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid integer population size: ");
            sc.next();
        }
        initialPopulationSize = sc.nextInt();
        ArrayList<String> population = createInitialPopulation(initialPopulationSize);
        while (generationCount < 10 && population.size() > 1) {
           ArrayList<String> matingPool = truncationSelect(population);
           matingPool = crossOver(matingPool);
           matingPool = mutation(matingPool, 4);
//            ArrayList<String> matingPool = rouletteSelection(population);
         //   ArrayList<String> matingPool = tournamentSelection(population);
            population.clear();
            population.addAll(matingPool);
            System.out.println(population);

            generationCount++;
        }


    }

    public static ArrayList<String> mutation (ArrayList<String> individuals, int mutationRate) {
        for (int i = 0; i < individuals.size(); i += (mutationRate * 2)) {
            System.out.println("the individual before mutation: " + individuals.get(i));
            int indexOne = (int) (Math.random() * 40);
            if (indexOne != 0 && indexOne % 2 == 1) {
                indexOne -= 1;
            } else if (indexOne % 2 == 1) {
                indexOne += 1;
            }
            int indexTwo = (int) (Math.random() * 40);
            if (indexTwo != 0 && indexTwo % 2 == 1) {
                indexTwo -= 1;
            } else if (indexTwo % 2 == 1) {
                indexTwo += 1;
            }
            char charOne = individuals.get(i).charAt(indexOne);
            char charTwo = individuals.get(i).charAt(indexTwo);
//            System.out.println(indexOne);
//            System.out.println(indexTwo);
//            System.out.println(charOne);
//            System.out.println(charTwo);
            String newIndividual = "";
            for (int j = 0; j < individuals.get(i).length(); j++) {
                if (j == indexOne) {
                    newIndividual += charTwo;
                } else if (j == indexTwo) {
                    newIndividual += charOne;
                } else {
                    newIndividual += individuals.get(i).charAt(j);
                }
            }
            individuals.remove(individuals.get(i));
            individuals.add(newIndividual);
            System.out.println("the individual after mutation: " + newIndividual);
        }
        return individuals;

    }

    public static ArrayList<String> crossOver (ArrayList<String> individuals) {
        System.out.println("population after selection, before crossover: " + individuals);
        ArrayList<String> matingPool = new ArrayList<>();
        if (individuals.size() % 2 == 1) {
            matingPool.add(individuals.get(0));
            individuals.remove(individuals.get(0));
        }
        for (int i = 0; i < individuals.size(); i += 2) {
            String tourOne = individuals.get(i);
            System.out.println("tour one: " + tourOne);
            String tourTwo = individuals.get(i + 1);
            System.out.println("tour two: " + tourTwo);
            String newIndividual = "";
            for (int j = 0; j < 20; j++) {
                newIndividual += tourOne.charAt(j);

            }
            System.out.println("new individual half complete: " + newIndividual);
            for (int k = 0; k < tourTwo.length(); k++) {
                if (!newIndividual.contains(String.valueOf(tourTwo.charAt(k)))) {
                    newIndividual += tourTwo.charAt(k);
                    if (newIndividual.length() < 39) {
                        newIndividual += ",";
                    }

                }
            }
            System.out.println("new individual complete: " + newIndividual);
            matingPool.add(newIndividual);
        }
        return matingPool;
    }

    public static ArrayList<String> createInitialPopulation(int initialPopulationSize) {
        boolean duplicateLocation = false;
        String locations = "ABCDEFGHIJKLMNOPQRST";
        ArrayList<String> population = new ArrayList<>();
        while (population.size() < initialPopulationSize) {
            String individual = "";
            while (individual.length() < 39) {
                int letterIndex = (int) (Math.random() * 20);
                for (int i = 0; i < individual.length(); i++) {
                    if (locations.charAt(letterIndex) == individual.charAt(i)) {
                        //terminating function if location is already in the individual
                        duplicateLocation = true;
                        i = individual.length();
                    }
                }
                if (!duplicateLocation) {
                    individual += locations.charAt(letterIndex);
                    if (individual.length() <= 38) {
                        individual += ",";
                    }

                } else {
                    duplicateLocation = false;
                }
            }
            population.add(individual);
        }
        System.out.println("initial population is: " + population);
        return population;
    }

    public static int[][] distances = {
            {0,94,76,141,91,60,120,145,91,74,90,55,145,108,41,49,33,151,69,111,24},
            {94,0,156,231,64,93,108,68,37,150,130,57,233,26,62,140,61,229,120,57,109},
            {76,156,0,80,167,133,124,216,137,114,154,100,141,161,116,37,100,169,49,185,84},
            {141,231,80,0,229,185,201,286,216,139,192,178,113,239,182,92,171,155,128,251,137},
            {91,64,167,229,0,49,163,65,96,114,76,93,200,91,51,139,72,185,148,26,92},
            {60,93,133,185,49,0,165,115,112,65,39,91,151,117,39,99,61,139,128,75,49},
            {120,108,124,201,163,165,0,173,71,194,203,74,254,90,127,136,104,269,75,163,144},
            {145,68,216,286,65,115,173,0,103,179,139,123,265,83,104,194,116,250,186,39,152},
            {91,37,137,216,96,112,71,103,0,160,151,39,236,25,75,130,61,239,95,93,112},
            {74,150,114,139,114,65,194,179,160,0,54,127,86,171,89,77,99,80,134,140,50},
            {90,130,154,192,76,39,203,139,151,54,0,129,133,155,78,117,99,111,159,101,71},
            {55,57,100,178,93,91,74,123,39,127,129,0,199,61,53,91,30,206,63,101,78},
            {145,233,141,113,200,151,254,265,236,86,133,199,0,251,171,118,176,46,182,226,125},
            {108,26,161,239,91,117,90,83,25,171,155,61,251,0,83,151,75,251,119,81,127},
            {41,62,116,182,51,39,127,104,75,89,78,53,171,83,0,90,24,168,99,69,49},
            {449,140,37,92,139,99,136,194,130,77,117,91,118,151,90,0,80,139,65,159,50},
            {33,61,100,171,72,61,104,116,61,99,99,30,176,75,24,80,0,179,76,86,52},
            {151, 229, 169, 155, 185, 139, 269, 250, 239, 80, 111, 206, 46, 251, 168, 139, 179, 0, 202, 211, 128},
            {69,120,49,128,148,128,75,186,95,134,159,63,182,119,99,65,76,202,0,161,90},
            {111,57,185,251,26,75,163,39,93,140,101,101,226,81,69,159,86,211,161,0,115},
            {24,109,84,137,92,49,144,152,112,50,71,78,125,127,49,50,52,128,90,115,0}
    };

    // format individuals like this: A,T,D,B,G... (Make sure there are no spaces)
    public static int fitLevel(String individual)
    {
        //It is assumed that all start and end with x
        individual = "X," + individual + ",X";
        Scanner sc = new Scanner(individual);
        sc.useDelimiter(",");
        String charOne = sc.next();
        String charTwo = sc.next();
        int city1 = convertToInt(charOne);
        int city2 = convertToInt(charTwo);
        int distance = distances[city1][city2];
        while(sc.hasNext())
        {
            city1 = city2;
            city2 = convertToInt(sc.next());
            distance += distances[city1][city2];

        }
        return distance;
    }

    private static int convertToInt(String city)
    {
        if(city.equals("X"))
        {
            return 0;
        }
        char[] tempc = city.toCharArray();
        char c = tempc[0];
        return (c - 64);
        // A = 65
    }

    public static ArrayList<String> rouletteSelection(ArrayList<String> individuals) {
        int numTaken;
        if (individuals.size() >= 3) {
            numTaken = individuals.size()/3;
        } else {
            numTaken = 1;
        }

        //First step is to order sort the individuals
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        int[] fitness = new int[individuals.size()];
        for(int i = 0; i < individuals.size(); i++)
        {
            int fit = fitLevel(individuals.get(i));
            fitness[i] = fit;
            map.put(fit, individuals.get(i));
        }
        bubbleSort(fitness);
        TreeMap<Double, Integer> fitnessPercentages = new TreeMap<>();
        double totalToDivideFrom = (individuals.size() * (1 + individuals.size()))/2;
        for (int i = 0; i < fitness.length; i++) {
            int index = i + 1;
                fitnessPercentages.put((index/totalToDivideFrom), fitness[i]);
        }
        System.out.println(Arrays.toString(fitness));
        System.out.println("fitnessPercentages: " + fitnessPercentages);
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < numTaken; i++) {
            double landingSpot = Math.random() * (individuals.size()/totalToDivideFrom);
            double percentage;
            Iterator iterator = fitnessPercentages.keySet().iterator();
            while (iterator.hasNext()) {
                percentage = (double) iterator.next();
                System.out.println("percentage is currently "+ percentage);
                if (percentage > landingSpot) {
                    temp.add(map.get(fitnessPercentages.get(percentage)));
                    break;
                }
            }
        }


        return temp;
    }

    public static ArrayList<String> tournamentSelection(ArrayList<String> individuals)
    {
        int numTaken;
        if (individuals.size() >= 3) {
            numTaken = individuals.size()/3;
        } else {
            numTaken = 1;
        }
        ArrayList<String> bigPool = new ArrayList<String>();
        String[] miniPool;
        int[] miniFit;
        miniPool = new String[4];
        miniFit = new int[4];

        Random rand = new Random();
        for(int i = 0; i < numTaken; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                int randNum = rand.nextInt(individuals.size());
                miniPool[j] = (individuals.get(randNum));
                miniFit[j] = fitLevel(individuals.get(randNum));
            }
            // get smallest from minipool
            int smallest = 0;
            int smallestIndex = 0;
            for(int j = 0; j < miniFit.length; j++)
            {
                if(miniFit[j] < smallest)
                {
                    smallest = miniFit[j];
                    smallestIndex = j;
                }
            }
            bigPool.add(miniPool[smallestIndex]);
        }

        return bigPool;
    }


    public static ArrayList<String> truncationSelect(ArrayList<String> individuals)
    {
        int numTaken;
        if (individuals.size() >= 3) {
            numTaken = individuals.size()/3;
        } else {
            numTaken = 1;
        }

        //First step is to order sort the individuals
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        int[] fitness = new int[individuals.size()];

        for(int i = 0; i < individuals.size(); i++)
        {
            int fit = fitLevel(individuals.get(i));
            fitness[i] = fit;
            map.put(fit, individuals.get(i));
        }

        bubbleSort(fitness);
        ArrayList<String> temp = new ArrayList<String>();

        for(int i = 0; i < numTaken; i++)
        {
            temp.add(map.get(fitness[i]));
        }

        return temp;

    }

    //bubble sort from slide deck and Geeks for Geeks
    private static void bubbleSort(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j+1] and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
    }


}
