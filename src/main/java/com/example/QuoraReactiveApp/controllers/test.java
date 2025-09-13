package com.example.QuoraReactiveApp.controllers;

//*********************  Merge sorted array **********************

import java.util.*;

public class test {
    public static void merge(int arr1[] , int arr2[] , int n , int m)
    {
        int left = n-1;
        int right = 0;

        while(left >= 0 && right < m)
        {
            if(arr1[left] > arr2[right])
            {
                int temp = arr1[left];
                arr1[left] = arr2[right];
                arr2[right] = temp;
                left--;
                right++;
            }
            else{
                break;
            }
        }

        Arrays.sort(arr1);
        Arrays.sort(arr2);
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of the 1st array :" );
        int n = sc.nextInt();
        int arr1[] = new int[n];
        System.out.println("Enter the elements :");
        for(int i=0 ; i<n ; i++)
        {
            arr1[i] = sc.nextInt();
        }
        System.out.println("Enter the size of the 2nd array :" );
        int m = sc.nextInt();
        int arr2[] = new int[m];
        System.out.println("Enter the elements :");
        for(int i=0 ; i<m ; i++)
        {
            arr2[i] = sc.nextInt();
        }

        merge(arr1, arr2, n, m);
        int[] result = new int[ n + m];

        for(int i=0 ; i<n ; i++)
        {
            System.out.println(result[i]+" ");
        }
        for(int i=0 ; i<m ; i++)
        {
            System.out.println(result[i]+" ");
        }
    }
}






//*************** Frecurency count **************************

// import java.util.*;
// public class test {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.println("Enter the size");
//         int n = sc.nextInt();

//         int[] arr= new int[n];
//         System.out.println("Enter elements :'");
//         for(int i=0 ; i<n ; i++)
//         {
//             System.out.println(arr[i]+" ");
//         }

//         HashMap<Integer , Integer> hash= new HashMap<>();
//         int count = 0;

//         for(int i=0 ; i<n ; i++)
//         {
//             int num = arr[i];
//             if(hash.containsKey(num))
//             {
//                 hash.put(num , hash.get(num)+1);
//             }
//             else{
//                 hash.put(num , 1);
//             }
//         }

//         for(int i=0 ; i<n ; i++)
//         {
//             System.out.println(num +" " + hash.get(num));
//         }

//     }
// }



//******************  fibonacci series ********************8


// import java.util.*;
// public class test {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.println("Enter the size");
//         int n = sc.nextInt();

//         int a = 0;
//         int b  =1;

//         for(int i=0 ; i<n ; i++)
//         {
//             System.out.println(a + " ");
//             int c = a+b;
//             a = b;
//             b = c;
//         }
//     }
// }