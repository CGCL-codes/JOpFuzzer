public final class Shallow {


    public static void main(final String[] args) {

/*
     Benchmark weather prediction program for comparing the
     performance of current supercomputers. The model is
     based on the paper - The dynamics of finite-difference
     models of the shallow water equations, by Robert Sadourny
     J. Atmos. Sci. Vol 32, No 4, April 1975

     Code by Paul N. Swartzrauber, NCAR, Oct 1984

     This version of the code taken from Hoffman et al., 1988
     Aspects of Using Multiprocessors for Meteorological Modelling
     in "Multiprocessing in Meteorological Models"
 */
        int n=256, m=n;
        int np1=n+1, mp1=m+1;
        double [][] u = new double[np1][mp1];
        double [][] v = new double[np1][mp1];
        double [][] p = new double[np1][mp1];
        double [][] unew = new double[np1][mp1];
        double [][] vnew = new double[np1][mp1];
        double [][] pnew = new double[np1][mp1];
        double [][] uold = new double[np1][mp1];
        double [][] vold = new double[np1][mp1];
        double [][] pold = new double[np1][mp1];
        double [][] cu = new double[np1][mp1];
        double [][] cv = new double[np1][mp1];
        double [][] z = new double[np1][mp1];
        double [][] h = new double[np1][mp1];
        double [][] psi = new double[np1][mp1];
        double t1, t2;

        double dt = 90.;
        double dx = 1.e5;
        double dy = 1.e5;
        double a = 1.e6;
        double alpha = 0.001;
        int itmax = 120;
        double el = n*dx;
        double pi = 4.*Math.atan(1.);
        double tpi = pi+pi;
        double di = tpi/m;
        double dj = tpi/n;
        double pcf = pi*pi*a*a/(el*el);
        double fsdx = 4./dx;
        double fsdy = 4./dy;
        int testsMax = 3;

        int i, j, it, tests; // Loop variables

        for ( tests=0; tests<testsMax; tests++ ) { // No of tests to run

            // two delta t (tdt) is set to dt on the first cycle
            // after which it is reset to dt+dt
            double tdt = dt;

            // Initial values of the stream function and p
            for ( j=0; j<np1; j++ ) {
                for ( i=0; i<mp1; i++ ) {
                    psi[j][i] = a*Math.sin((i+0.5)*di)*Math.sin((j+0.5)*dj);
                    p[j][i] = pcf*(Math.cos(2.*i*di) + Math.cos(2.*j*dj)) +
                            50000.;
                }
            }

            // Initialise velocities
            for ( j=0; j<n; j++ ) {
                for ( i=0; i<m; i++ ) {
                    u[j][i+1] = -( psi[j+1][i+1] - psi[j][i+1] ) / dy;
                    v[j+1][i] = ( psi[j+1][i+1] - psi[j+1][i] ) / dx;
                }
            }

            // Periodic continuation
            for ( j=0; j<n; j++ ) {
                u[j][0] = u[j][m];
                v[j+1][m] = v[j+1][0];
            }
            for ( i=0; i<m; i++ ) {
                u[n][i+1] = u[0][i+1];
                v[0][i] = v[n][i];
            }
            u[n][0] = u[0][m];
            v[0][m] = v[n][0];
            for ( j=0; j<np1; j++ ) {
                for ( i=0; i<mp1; i++ ) {
                    uold[j][i] = u[j][i];
                    vold[j][i] = v[j][i];
                    pold[j][i] = p[j][i];
                }
            }

            t1 = 1e-3*System.currentTimeMillis();
            for ( it=0; it<itmax; it++ ) {

                // Compute U, V and Z
                for ( j=0; j<n; j++ ) {
                    for ( i=0; i<m; i++ ) {
                        cu[j][i+1] = 0.5*(p[j][i+1]+p[j][i])*u[j][i+1];
                        cv[j+1][i] = 0.5*(p[j+1][i]+p[j][i])*v[j+1][i];
                        z[j+1][i+1] = ( fsdx*(v[j+1][i+1]-v[j+1][i]) -
                                fsdy*(u[j+1][i+1]-u[j][i+1])) /
                                (p[j][i]+p[j][i+1]+p[j+1][i+1]+p[j+1][i]);
                        h[j][i] = p[j][i] + 0.25 *(u[j][i+1]*u[j][i+1]+u[j][i]*u
                                [j][i]
                                + v[j+1][i]*v[j+1][i]+v[j][i]*v[j][i]);
                    }
                }

                // Periodic continuation
                for ( j=0; j<n; j++ ) {
                    cu[j][0] = cu[j][m];
                    cv[j+1][m] = cv[j+1][0];
                    z[j+1][0] = z[j+1][m];
                    h[j][m] = h[j][0];
                }
                for ( i=0; i<m; i++ ) {
                    cu[n][i+1] = cu[0][i+1];
                    cv[0][i] = cv[n][i];
                    z[0][i+1] = z[n][i+1];
                    h[n][i] = h[0][i];
                }
                cu[n][0] = cu[0][m];
                cv[0][m] = cv[n][0];
                z[0][0] = z[n][m];
                h[n][m] = h[0][0];

                // Compute new values u, v and p
                double tdts8 = tdt/8.;
                double tdtsdx = tdt/dx;
                double tdtsdy = tdt/dy;
                for ( j=0; j<n; j++ ) {
                    for ( i=0; i<m; i++ ) {
                        unew[j][i+1] = uold[j][i+1] +
                                tdts8*(z[j+1][i+1]+z[j][i+1]) *
                                        (cv[j+1][i+1]+cv[j+1][i]+cv[j][i]+cv[j][i+1]) -
                                tdtsdx*(h[j][i+1]-h[j][i]);
                        vnew[j+1][i] = vold[j+1][i] - tdts8*(z[j+1][i+1]+z[j+1]
                                [i])
                                *(cu[j+1][i+1]+cu[j+1][i]+cu[j][i]+cu[j][i+1])
                                -tdtsdy*(h[j+1][i]-h[j][i]);
                        pnew[j][i] = pold[j][i] - tdtsdx*(cu[j][i+1]-cu[j][i]) -
                                tdtsdy*(cv[j+1][i]-cv[j][i]);
                    }
                }

                // Periodic continuation
                for ( j=0; j<n; j++ ) {
                    unew[j][0] = unew[j][m];
                    vnew[j+1][m] = vnew[j+1][0];
                    pnew[j][m] = pnew[j][0];
                }
                for ( i=0; i<m; i++ ) {
                    unew[n][i+1] = unew[0][i+1];
                    vnew[0][i] = vnew[n][i];
                    pnew[n][i] = pnew[0][i];
                }
                unew[n][0] = unew[0][m];
                vnew[0][m] = vnew[n][0];
                pnew[n][m] = pnew[0][0];

                if ( it>0 ) {
                    for ( j=0; j<n; j++ ) {
                        for ( i=0; i<m; i++ ) {
                            uold[j][i] = u[j][i]+alpha*(unew[j][i]-2.*u[j][i]
                                    +uold[j][i]);
                            vold[j][i] = v[j][i]+alpha*(vnew[j][i]-2.*v[j][i]
                                    +vold[j][i]);
                            pold[j][i] = p[j][i]+alpha*(pnew[j][i]-2.*p[j][i]
                                    +pold[j][i]);
                            u[j][i] = unew[j][i];
                            v[j][i] = vnew[j][i];
                            p[j][i] = pnew[j][i];
                        }
                    }

                    // Periodic continuation
                    for ( j=0; j<n; j++ ) {
                        uold[j][m] = uold[j][0];
                        vold[j][m] = vold[j][0];
                        pold[j][m] = pold[j][0];
                        u[j][m] = u[j][0];
                        v[j][m] = v[j][0];
                        p[j][m] = p[j][0];
                    }
                    for ( i=0; i<m; i++ ) {
                        uold[n][i] = uold[0][i];
                        vold[n][i] = vold[0][i];
                        pold[n][i] = pold[0][i];
                        u[n][i] = u[0][i];
                        v[n][i] = v[0][i];
                        p[n][i] = p[0][i];
                    }
                    uold[n][m] = uold[0][0];
                    vold[n][m] = vold[0][0];
                    pold[n][m] = pold[0][0];
                    u[n][m] = u[0][0];
                    v[n][m] = v[0][0];
                    p[n][m] = p[0][0];
                }
                else {
                    tdt = tdt+tdt;
                    for ( j=0; j<np1; j++ ) {
                        for ( i=0; i<mp1; i++ ) {
                            uold[j][i] = u[j][i];
                            vold[j][i] = v[j][i];
                            pold[j][i] = p[j][i];
                            u[j][i] = unew[j][i];
                            v[j][i] = vnew[j][i];
                            p[j][i] = pnew[j][i];
                        }
                    }
                }
            }
            t2 = 1e-3*System.currentTimeMillis();
            double psum = 0.;
            for ( j=0; j<n; j++ )
                for ( i=0; i<m; i++ )
                    psum += p[j][i];
            System.out.println("PSUM: " + psum);
            // 65 FP operations per point per iteration
            System.out.println(" MFLOPS: " + 1e-6*65*m*n*itmax/(t2-t1));
        }

    }


}