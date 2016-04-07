package com.topeastic.mapreduce.job.vo;

public class LinePlot {
	
	 	int  z = 3;
	    int  w=1;
	    double x[] = {-3, -2, -1, 0, 1, 2, 3};
	    double y[] = {4, 2, 3, 0, -1, -2, -5};
	    double  a[][] = new double[z][x.length] ;
	    double b[][] = new double [x.length][z];
	    double c[][] = new double [z][z];
	    double e[]= new double [z];
	    double x1 , x2 , x3;
	    //依次按f(x)=1   f(x)=x    f(x)=x^2   写出矩阵A并输出
	    public LinePlot(double[] x, double[] y){
	    	this.x = x;
	    	this.y = y;
	    	this.a = new double[z][x.length] ;
	    	this.b = new double [x.length][z];
	    	printarrayA(a);
	        printarrayB(b);
	        ATA(a,b);
	        ATY(a,y);
	    	this.x = x;
	    	this.y = y;
	    }
	    
	    public void printarrayA(double a[ ][ ]){
	        for(int i=0; i<z; i++){
	            switch(i){
	            case 0: for(int j=0; j<x.length; j++)a[i][j]=1;
	            break;
	             
	            case 1: for(int j=0; j<x.length; j++) a[i][j] = x[j];
	            break;
	             
	            case 2: for(int j=0; j<x.length; j++)  a[i][j] = x[j]*x[j];
	            break;
	            }
	        }
	        System.out.println("矩阵A为：");
	        for(int i=0; i<z; i++){
	            for(int j=0; j<x.length; j++){
	                System.out.printf("%8s",a[i][j]+" ");
	                 
	            }
	            System.out.println();
	        }
	    }
	     
	    //              求A的转置  并输出
	    public void printarrayB(double b[][]){
	        System.out.println("A的转置矩阵为");
	        for(int i=0; i<z; i++){
	            for(int j=0; j<x.length; j++){
	                b[j][i] = a[i][j] ; 
	            }
	        }
	     
	        for(int i=0; i<x.length; i++){
	                for(int j=0; j<z; j++){
	                System.out.printf("%8s",b[i][j]+" ");
	                }
	                System.out.println();
	        }
	    }
	     
	                                //      矩阵A和它转置矩阵相乘
	                public void ATA(double a[][], double b[][]){
	                       System.out.println("A^T乘以A（即方程左端系数矩阵） =");
	                       for(int p=0; p<z; p++)
	                       {
	                           for(int q=0; q<z; q++)
	                           {   
	                               for(int i=0; i<x.length; i++)
	                               {
	                                      c[p][q] += a[p][i]*b[i][q];
	                               }
	                                System.out.printf("%8s", c[p][q]+" ");
	                           }
	                           System.out.println();
	                      }
	                             
	                    }
	                //   矩阵A的转置乘y      设为E
	                public void ATY(double a[ ][ ] , double y[ ]){
	                        System.out.println("矩阵E（方程右端）为");
	                        for(int i=0; i<z; i++)
	                        {
	                            for(int j=0; j<y.length; j++)
	                            {
	                                e[i] += a[i][j] * y[j];
	                            }
	                            System.out.printf("%8s",e[i]+"\n");
	                        }
	                     
	                }
	                /*
	                 * 此时已经得到 法方程组
	                 * 用克莱姆法则解此方程组
	                 * 求出P2(x)的系数 h1  h2  h3即可写出拟合函数
	                */
	                public  Plot  getCoefficient(){
	                     //求出矩阵的行列式值
	                	Plot p = new Plot();
	                        double A;
	                         A=c[0][0]*c[1][1]*c[2][2]+c[1][0]*c[2][1]*c[0][2]+c[0][1]*c[1][2]*c[2][0]
	                                 -c[0][2]*c[1][1]*c[2][0]-c[2][1]*c[1][2]*c[0][0]-c[1][0]*c[0][1]*c[2][2];
	                         double f1 [ ][ ] =new double[z][z];
	                         double f2 [ ][ ] =new double[z][z];
	                         double f3 [ ][ ] =new double[z][z];
	                                                                                                  //    System.out.println(A);
	                         //计算h1
	                         for(int i=0; i<z; i++)
	                         {
	                                 switch(i)
	                                 {
	                                 case 0: for(int j=0; j<z; j++) f1[j][i] = e[j];
	                                 break;
	                                 case 1: for(int j=0; j<z; j++) f1[j][i] = c[j][i];
	                                 break;
	                                 case 2: for(int j=0; j<z; j++) f1[j][i] = c[j][i];
	                                 break;
	                                 }
	                         }
	                         double B1;
	                         B1 = f1[0][0]*f1[1][1]*f1[2][2]+f1[1][0]*f1[2][1]*f1[0][2]+f1[0][1]*f1[1][2]*f1[2][0]
	                                 -f1[0][2]*f1[1][1]*f1[2][0]-f1[2][1]*f1[1][2]*f1[0][0]-f1[1][0]*f1[0][1]*f1[2][2];
	                         x1 =B1/A;
	                         p.setX1(x1);
	                         System.out.print("h1="+x1+"    ");
	                         
	                         //计算h2
	                         for(int i=0; i<z; i++)
	                         {
	                                 switch(i)
	                                 {
	                                 case 0: for(int j=0; j<z; j++) f2[j][i] = c[j][i];
	                                 break;
	                                 case 1: for(int j=0; j<z; j++) f2[j][i] = e[j];
	                                 break;
	                                 case 2: for(int j=0; j<z; j++) f2[j][i] = c[j][i];
	                                 break;
	                                 }
	                         }
	                         double B2;
	                         B2 = f2[0][0]*f2[1][1]*f2[2][2]+f2[1][0]*f2[2][1]*f2[0][2]+f2[0][1]*f2[1][2]*f2[2][0]
	                                 -f2[0][2]*f2[1][1]*f2[2][0]-f2[2][1]*f2[1][2]*f2[0][0]-f2[1][0]*f2[0][1]*f2[2][2];
	                         x2 =B2/A;
	                         p.setX2(x2);
	                         System.out.print("h2="+x2+"    ");
	                          
	                         //计算h3
	                         for(int i=0; i<z; i++)
	                         {
	                                 switch(i)
	                                 {
	                                 case 0: for(int j=0; j<z; j++) f3[j][i] = c[j][i];
	                                 break;
	                                 case 1: for(int j=0; j<z; j++) f3[j][i] = c[j][i];
	                                 break;
	                                 case 2: for(int j=0; j<z; j++) f3[j][i] = e[j];
	                                 break;
	                                 }
	                         }
	                         double B3;
	                         B3 = f3[0][0]*f3[1][1]*f3[2][2]+f3[1][0]*f3[2][1]*f3[0][2]+f3[0][1]*f3[1][2]*f3[2][0]
	                                 -f3[0][2]*f3[1][1]*f3[2][0]-f3[2][1]*f3[1][2]*f3[0][0]-f3[1][0]*f3[0][1]*f3[2][2];
	                         x3 =B3/A;
	                         p.setX3(x3);
	                         System.out.println("h3="+x3+"  ");
	                          
	                         System.out.println("所求得 P2(x)="+x1+x2+"x"+x3+"x^2");
	                         return p;
	                }
	                 
	 
	 
	        public LinePlot(){
	            printarrayA(a);
	            printarrayB(b);
	            ATA(a,b);
	            ATY(a,y);
	            getCoefficient();
	        }
	     
	     
	    public static void main(String args[]){
	        new LinePlot();
	    }
}
