/*
 *   Name: Cedric Jo
 *   Longest Common Substring
 *
 */

package st;

import java.util.*;


public class ST {

    int numString;
    String str;
    int strLength;
    ArrayList<Integer> size;
    String sfxStr;
    int N;
    
    
    ST(int numStr, String s) {
        this.numString = numStr;
        this.str = s;
        this.strLength = s.length();
        this.size = new ArrayList(numStr);
        sfxStr = "";
        
        suffixT();
    }
    
    
    public void suffixT() {
        //System.out.println(str);
        int temp = 0;
        for (int i=0; i < strLength; i++) {
            if (str.charAt(i) == '$') {
                size.add(i-temp);
                //System.out.println("str = " + str.substring(temp, i));
                if (i == strLength-1) {
                    sfxStr += str.substring(temp, i);
                }
                else {
                    sfxStr += str.substring(temp, i) + '\1'; 
                }
                temp = i+1;
            }
        }
        N = sfxStr.length();
        
        SuffixArray suffix = new SuffixArray(sfxStr);
        
        // search for longest common substring
        String lcs = "";
        for (int i = 1; i < N; i++) {
          
            if (suffix.index(i) < size.get(0) && suffix.index(i-1) < size.get(0)) continue;

            if (suffix.index(i) > size.get(0) && suffix.index(i-1) > size.get(0)) continue;

            int length = suffix.lcp(i);
            if (length > lcs.length()) {
                lcs = sfxStr.substring(suffix.index(i), suffix.index(i) + length);
            }
        }
     
        System.out.println("\n" + "The longest common substring is " + lcs + ".");   
        
        System.out.print("\n" + lcs + " starts at: ");
        
        
        int index = 0;
        int x = 1;
        index = str.indexOf(lcs);
        System.out.print("Position " + (index+1) + " of String " + x);
        x++;
        int z = 0;
        int tmp = index;
        
        while (x != numString+1) {
            for (int i=tmp; i < str.length(); i++) {
                if (str.charAt(i) == '$') {
                    z = i; break;}
            }
                
            tmp = str.indexOf(lcs, z);
            index = tmp-(z+1);
            System.out.print(", Position " + (index+1) + " of String " + x);
  
            x++;            
        }
        System.out.println();
    }
    
    
/*============================================================================*/   
public class SuffixArray {
    private Suffix[] suffixes;

/*---------------------------------------------------------------------------*/
    private class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }
        
        private int length() {
            return text.length() - index;
        }
        
        private char charAt(int i) {
            return text.charAt(index + i);
        }
        
        public int compareTo(Suffix that) {
            if (this == that) return 0;  // optimization
            int N = Math.min(this.length(), that.length());
            for (int i = 0; i < N; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }
    }
/*---------------------------------------------------------------------------*/

    public SuffixArray(String text) {
        int n = text.length();
        this.suffixes = new Suffix[n];
        for (int i = 0; i < n; i++)
            suffixes[i] = new Suffix(text, i);
        Arrays.sort(suffixes);
    }

    public int length() {
        return suffixes.length;
    }

    public int index(int i) {
        return suffixes[i].index;
    }

    public int lcp(int i) {
        return lcp(suffixes[i], suffixes[i-1]);
    }

    // longest common prefix of s and t
    private int lcp(Suffix s, Suffix t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) return i;
        }
        return n;
    }  
}
/*============================================================================*/     

    
    public static void main(String[] args) throws Exception {
        int numStr;
        String s = "";
        
       
        Scanner in = new Scanner(System.in);
        
        System.out.print("Please Enter the Number of Strings: ");
        numStr = in.nextInt();
        
        for (int i=1; i <= numStr; i++) {
            System.out.print("Enter String " + i + ": ");
            s += in.next() + "$";
        }
        
        ST st = new ST(numStr, s);
        
    }
    
}



