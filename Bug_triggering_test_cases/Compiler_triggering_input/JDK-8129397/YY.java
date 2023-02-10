class YY {

    static final java.util.Random R=new java.util.Random(1);


    double runTests(final int ntests)
    {
        double yy=0;
        for(int k=0;k<ntests;k++)
        {
            final int d=R.nextInt(23)+3;
            final double [] m=new double [d];
            for(int j=0;j<d;j++) m[j]=R.nextDouble();
            final double [] v=getX(m);
            for(int j=0;j<v.length;j++) yy+=v[j];
        }
        return yy;
    }



    public double [] getX(final double [] moms)
    {
        final double [] r=new double [moms.length-1];
        final double [] rmoms=new double [moms.length];
        //System.err.println("INIT:rmoms="+rmoms+" moms="+moms+" r="+r);
        for(int k=0;k<r.length;k++)
        {
            final double [] tmp=new double [k+1];
            tmp[k]=1;
            setxP(rmoms,tmp);
            System.err.println("k="/*+k*/+" rmoms="+rmoms+" moms="+moms+" r="+r);
            r[k]=mulVV(k+2,rmoms,moms); // Line 544 !!! Arguments are null !!!
        }
        return r;
    }

    public void setxP(final double [] r,final double [] x)
    {
        setxP(x.length,r,x);
    }

    static final YY BASIS=new YY();

    public void setxP(final int n,final double [] res,final double [] p)
    {
        BASIS.setaxbP(n-1,res,p,1,0);
    }

    public void setaxbP(final int n,final double [] res,final double [] p,final double a,final double b)
    {
        double r=p[n];
        res[n]=res[n+1]=0;
        for(int k=n;k>=0;k--)
        {
            final double ar=a*r;
            res[k+1]-=(k+1)*(ar);
            res[k]+=(2*k+1)*(ar)+b*r;
            if(k>0)
            {
                final double t=p[k-1];
                res[k-1]=-k*(ar);
                r=t;
            }
        }
    }

    public static double mulVV(final int n,final double [] a,final double [] b)
    {
        return mulVV(n,a,0,b,0);
    }

    public static double mulVV(final int n,final double [] a,final int off_a,final double [] b,final int off_b)
    {
        double s=0;
        for(int i=n;--i>=0;)
        {
            s+=a[i+off_a]*b[i+off_b];
        }
        return s;
    }



    public static void main(String [] args)
    {
        final int ntests=10000000;
        final double y=new YY().runTests(ntests);
        System.err.println("y="+y);
    }

}