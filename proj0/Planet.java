public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet P) {
		xxPos = P.xxPos;
		yyPos = P.yyPos;
		xxVel = P.xxVel;
		yyVel = P.yyVel;
		mass = P.mass;
		imgFileName = P.imgFileName;
	}

	public double calcDistance(Planet P) {
		double a = (P.xxPos - this.xxPos) * (P.xxPos - this.xxPos); // Conduct squaring without Math.pow
		double b = (P.yyPos - this.yyPos) * (P.yyPos - this.yyPos);
		double r = Math.sqrt(a + b);
		return r;
	}

	public double calcForceExertedBy(Planet P) {
		double G = 6.67e-11;
		double F = (G * P.mass * this.mass) / (this.calcDistance(P) * this.calcDistance(P));
		return F;
	}

	public double calcForceExertedByX(Planet P) {
		double Fx = (this.calcForceExertedBy(P) * (P.xxPos - this.xxPos)) / this.calcDistance(P);
		return Fx;
	}

	public double calcForceExertedByY(Planet P) {
		double Fy = (this.calcForceExertedBy(P) * (P.yyPos - this.yyPos)) / this.calcDistance(P);
		return Fy;
	}

	public double calcNetForceExertedByX(Planet P[]) {
		double Fnet_x = 0.0;	
		for (int i = 0; i < P.length; i++) {
			if (this.equals(P[i])) {
				continue;
			} else {
				double Fx = this.calcForceExertedByX(P[i]);
				Fnet_x += Fx;
			}			
		}
		return Fnet_x;
	}

	public double calcNetForceExertedByY(Planet P[]) {
		double Fnet_y = 0.0;
		for (int i = 0; i < P.length; i++) {
			if (this.equals(P[i])) {
				continue;
			} else {
				double Fy = this.calcForceExertedByY(P[i]);
				Fnet_y += Fy;
			}	
		}
		return Fnet_y;
	}

	public void update(double dt, double fX, double fY) {
		// Calcualte the acceleration
		double ax = fX / this.mass;
		double ay = fY / this.mass;

		// Calculate the new velocity
		this.xxVel = this.xxVel + dt * ax;
		this.yyVel = this.yyVel + dt * ay;

		// Calculate the new position
		this.xxPos = this.xxPos + dt * xxVel;
		this.yyPos = this.yyPos + dt * yyVel;
	}

	public void draw() {
		String image = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, image);
		StdDraw.show();
	}

}