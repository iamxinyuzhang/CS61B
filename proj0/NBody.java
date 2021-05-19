public class NBody {
	public static double readRadius(String s) {
		In in = new In(s);

		int num_planet = in.readInt(); 
		double radius = in.readDouble();

		return radius;
	}

	public static Planet[] readPlanets(String s) {
		In in = new In(s);

		int num_planet = in.readInt(); 
		double radius = in.readDouble();
		Planet[] p = new Planet[num_planet]; 

		for (int i = 0; i < num_planet; i += 1) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName= in.readString();
			p[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
		}
		return p;		
	}

	public static void main(String[] args) {

		// Collecting all needed input
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		Planet[] planets = readPlanets(filename);
		double radius = readRadius(filename);

		// Drawing the Background
		String image = "images/starfield.jpg";
		StdDraw.clear();
		StdDraw.setScale(-radius, radius); // The universe goes from (-radius, -radius) to (radius, radius)
		StdDraw.picture(0,0,image);
		StdDraw.show();
		StdDraw.pause(1000);

		// Drawing All of the Planets
		for (int i = 0; i < planets.length; i += 1) {
			planets[i].draw();
		 }

		StdDraw.enableDoubleBuffering(); // All drawing takes place on the offscreen canvas for smooth animation
		int t = 0;
		while (t < T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for (int j = 0; j < planets.length; j += 1) {
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);
			}

			for (int m = 0; m < planets.length; m += 1) {
				planets[m].update(dt, xForces[m], yForces[m]);
			}

			String image1 = "images/starfield.jpg";
			StdDraw.clear();
			StdDraw.setScale(-radius, radius); // The universe goes from (-radius, -radius) to (radius, radius)
			StdDraw.picture(0,0,image1);
			StdDraw.show();

			for (int i = 0; i < planets.length; i += 1) {
				planets[i].draw();
			 }

			StdDraw.pause(10);
			t += dt;
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
				planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}


	}
	// javac -Xlint:deprecation -encoding UTF-8 NBody.java	 
}