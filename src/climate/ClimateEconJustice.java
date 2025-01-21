package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String[] split = inputLine.split(",");
        StateNode newState = new StateNode(split[2], null, null);
        StateNode curr = getFirstState();
        if (curr==null){
            firstState = newState;
        }
        while (curr!=null){
            if (newState.getName().equals(curr.getName())){
                break;
            }
            if (curr.getNext()==null){
                curr.setNext(newState);
                break;
            }
            curr = curr.getNext();
        }

    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] split = inputLine.split(",");
        StateNode theState = getFirstState();
        CountyNode newCounty = new CountyNode(split[1], null, null);
        while (theState != null){
            if (theState.getName().equals(split[2])){
            break;
            }
            theState = theState.getNext();
        }
        CountyNode curr = theState.getDown();
        if (curr==null){
            theState.setDown(newCounty);
        }
        while (curr != null){
            if (curr.equals(newCounty)){
                break;
            } else if (curr.getNext() == null){
                curr.setNext(newCounty);
                break;
            } else {
                curr = curr.getNext();
            }
        }

    }

    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        String[] split = inputLine.split(",");
        StateNode theState = getFirstState();
        double aa = Double.parseDouble(split[3]);
        double n = Double.parseDouble(split[4]);
        double a = Double.parseDouble(split[5]);
        double w = Double.parseDouble(split[8]);
        double h = Double.parseDouble(split[9]);
        double pm = Double.parseDouble(split[49]);
        double cof = Double.parseDouble(split[37]);
        double pl = Double.parseDouble(split[121]);
        Data newData = new Data(aa, n, a, w, h, split[19], pm, cof, pl);
        CommunityNode newCommunity = new CommunityNode(split[0], null, newData);
        while (theState != null){
            if (theState.getName().equals(split[2])){
            break;
            }
            theState = theState.getNext();
        }
        CountyNode theCounty = theState.getDown();
        while (theCounty != null){
            if (theCounty.getName().equals(split[1])){
            break;
            }
            theCounty = theCounty.getNext();
        }
        CommunityNode curr = theCounty.getDown();
        if (curr == null){
            theCounty.setDown(newCommunity);
        }
        while (curr != null){
            if (curr.equals(newCommunity)){
                break;
            } else if (curr.getNext() == null){
                curr.setNext(newCommunity);
                break;
            } else {
                curr = curr.getNext();
            }
        }

    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        int count = 0;
        StateNode currState = getFirstState();
        CountyNode currCounty;
        CommunityNode currComm;
        Data thisData;
        double r = 0;
        
        while (currState != null){
            currCounty = currState.getDown();
            while (currCounty != null){
                currComm = currCounty.getDown();
                    while (currComm != null){
                        thisData = currComm.getInfo();
                        if (race.equals("African American")){
                            r = thisData.getPrcntAfricanAmerican();
                        } else if (race.equals("Native American")){
                            r = thisData.getPrcntNative();
                        } else if (race.equals("Asian American")){
                            r = thisData.getPrcntAsian();
                        } else if (race.equals("White American")){
                            r = thisData.getPrcntWhite();
                        } else if (race.equals("Hispanic American")){
                            r = thisData.getPrcntHispanic();
                        } 
                        if (r*100 >= userPrcntage && thisData.getAdvantageStatus().equals("True")){
                            count++;
                        }
                        currComm = currComm.getNext();
                    }
                currCounty = currCounty.getNext();
            }
            currState = currState.getNext();
        }

        return count; // replace this line
    }

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

        int count = 0;
        StateNode currState = getFirstState();
        CountyNode currCounty;
        CommunityNode currComm;
        Data thisData;
        double r = 0;
        
        while (currState != null){
            currCounty = currState.getDown();
            while (currCounty != null){
                currComm = currCounty.getDown();
                    while (currComm != null){
                        thisData = currComm.getInfo();
                        if (race.equals("African American")){
                            r = thisData.getPrcntAfricanAmerican();
                        } else if (race.equals("Native American")){
                            r = thisData.getPrcntNative();
                        } else if (race.equals("Asian American")){
                            r = thisData.getPrcntAsian();
                        } else if (race.equals("White American")){
                            r = thisData.getPrcntWhite();
                        } else if (race.equals("Hispanic American")){
                            r = thisData.getPrcntHispanic();
                        } 
                        if (r*100 >= userPrcntage && thisData.getAdvantageStatus().equals("False")){
                            count++;
                        }
                        currComm = currComm.getNext();
                    }
                currCounty = currCounty.getNext();
            }
            currState = currState.getNext();
        }

        return count; // replace this line
    }
    
    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList<StateNode> pml = new ArrayList<>();
        StateNode currState = getFirstState();
        CountyNode currCounty;
        CommunityNode currComm;
        Data thisData;
        
        while (currState != null){
            currCounty = currState.getDown();
            innerLoops:
            while (currCounty != null){
                currComm = currCounty.getDown();
                    while (currComm != null){
                        thisData = currComm.getInfo();
                        if (PMlevel <= thisData.getPMlevel()){
                            pml.add(currState);
                            break innerLoops;
                        }
                        currComm = currComm.getNext();
                    }
                currCounty = currCounty.getNext();
            }
            currState = currState.getNext();
        }

        return pml; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        int count = 0;
        StateNode currState = getFirstState();
        CountyNode currCounty;
        CommunityNode currComm;
        Data thisData;
        
        while (currState != null){
            currCounty = currState.getDown();
            while (currCounty != null){
                currComm = currCounty.getDown();
                    while (currComm != null){
                        thisData = currComm.getInfo();
                        double chance = thisData.getChanceOfFlood();
                        if (chance >= userPercntage){
                            count++;
                        }
                        currComm = currComm.getNext();
                    }
                currCounty = currCounty.getNext();
            }
            currState = currState.getNext();
        }

        return count; // replace this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        ArrayList<CommunityNode> lowest = new ArrayList<>();
        StateNode currState = getFirstState();
        CommunityNode currComm;
        Data thisData;

        while (!currState.getName().equals(stateName)){
            currState = currState.getNext();
        }

        double min = 1000;
        int mindex = 0;
        double poverty;
        CountyNode currCounty = currState.getDown();
        while (currCounty != null){
            currComm = currCounty.getDown();
                while (currComm != null){
                    thisData = currComm.getInfo();
                    poverty = thisData.getPercentPovertyLine();
                    if (lowest.size()<10) {
                        lowest.add(currComm);
                    } else { 
                        min = lowest.get(0).getInfo().getPercentPovertyLine();
                        mindex = 0;
                        
                        for (int i = 1; i < 10; i++){
                            double currpov = lowest.get(i).getInfo().getPercentPovertyLine();
                            if (currpov<min){
                                min = currpov;
                                mindex = i;
                            }
                        }
                        if (poverty>min) lowest.set(mindex, currComm);  
                    }
                    currComm = currComm.getNext();
                }
            currCounty = currCounty.getNext();
            }


        return lowest; // replace this line
    }
}
    
