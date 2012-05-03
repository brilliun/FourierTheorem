package util;

public class FourierTransformUtil {

	public static Complex[][] FFT2D(int n, int m, boolean inverse, Complex[][] data) {

		Complex[][] ftData = new Complex[n][m];

		int l2n = 0, p = 1; // l2n will become log_2(n)
		while (p < n) {
			p *= 2;
			l2n++;
		}
		int l2m = 0;
		p = 1; // l2m will become log_2(m)
		while (p < m) {
			p *= 2;
			l2m++;
		}

		m = 1 << l2m;
		n = 1 << l2n; // Make sure m and n will be powers of 2, otherwise you'll
						// get in an infinite loop

		// Erase all history of this array
		for (int x = 0; x < m; x++)
			// for each column
			for (int y = 0; y < m; y++) // for each row
			{
				
				ftData[x][y] = new Complex(data[x][y].getRe(), data[x][y].getIm());


			}

		// Bit reversal of each row

		for (int y = 0; y < m; y++) // for each row
		{
			int j = 0;
			for (int i = 0; i < n - 1; i++) {
				
				ftData[i][y] = new Complex(data[j][y].getRe(), data[j][y].getIm());
				

				int k = n / 2;
				while (k <= j) {
					j -= k;
					k /= 2;
				}
				j += k;
			}
		}
		// Bit reversal of each column
		double tx = 0, ty = 0;
		for (int x = 0; x < n; x++) // for each column
		{
			int j = 0;
			for (int i = 0; i < m - 1; i++) {
				if (i < j) {
					tx = ftData[x][i].getRe();
					ty = ftData[x][i].getIm();
					ftData[x][i].setRe(ftData[x][j].getRe());
					ftData[x][i].setIm(ftData[x][j].getIm());
					ftData[x][j].setRe(tx);
					ftData[x][j].setIm(ty);
				}
				int k = m / 2;
				while (k <= j) {
					j -= k;
					k /= 2;
				}
				j += k;
			}
		}

		// Calculate the FFT of the columns
		for (int x = 0; x < n; x++) // for each column
		{
			// This is the 1D FFT:
			double ca = -1.0;
			double sa = 0.0;
			int l1 = 1, l2 = 1;
			for (int l = 0; l < l2n; l++) {
				l1 = l2;
				l2 *= 2;
				double u1 = 1.0;
				double u2 = 0.0;
				for (int j = 0; j < l1; j++) {
					for (int i = j; i < n; i += l2) {
						int i1 = i + l1;
						double t1 = u1 * ftData[x][i1].getRe() - u2 * ftData[x][i1].getIm();
						double t2 = u1 * ftData[x][i1].getIm() + u2 * ftData[x][i1].getRe();
						ftData[x][i1].setRe(ftData[x][i].getRe() - t1);
						ftData[x][i1].setIm(ftData[x][i].getIm() - t2);
						ftData[x][i].setRe(ftData[x][i].getRe() + t1);
						ftData[x][i].setIm(ftData[x][i].getIm() + t2);
					}
					double z = u1 * ca - u2 * sa;
					u2 = u1 * sa + u2 * ca;
					u1 = z;
				}
				sa = Math.sqrt((1.0 - ca) / 2.0);
				if (!inverse)
					sa = -sa;
				ca = Math.sqrt((1.0 + ca) / 2.0);
			}
		}
		// Calculate the FFT of the rows
		for (int y = 0; y < m; y++) // for each row
		{
			// This is the 1D FFT:
			double ca = -1.0;
			double sa = 0.0;
			int l1 = 1, l2 = 1;
			for (int l = 0; l < l2m; l++) {
				l1 = l2;
				l2 *= 2;
				double u1 = 1.0;
				double u2 = 0.0;
				for (int j = 0; j < l1; j++) {
					for (int i = j; i < n; i += l2) {
						int i1 = i + l1;
						double t1 = u1 * ftData[i1][y].getRe() - u2 * ftData[i1][y].getIm();
						double t2 = u1 * ftData[i1][y].getIm() + u2 * ftData[i1][y].getRe();
						ftData[i1][y].setRe(ftData[i][y].getRe() - t1);
						ftData[i1][y].setIm(ftData[i][y].getIm() - t2);
						ftData[i][y].setRe(ftData[i][y].getRe() + t1);
						ftData[i][y].setIm(ftData[i][y].getIm() + t2);
					}
					double z = u1 * ca - u2 * sa;
					u2 = u1 * sa + u2 * ca;
					u1 = z;
				}
				sa = Math.sqrt((1.0 - ca) / 2.0);
				if (!inverse)
					sa = -sa;
				ca = Math.sqrt((1.0 + ca) / 2.0);
			}
		}

		int d;
		if (inverse)
			d = n;
		else
			d = m;
		for (int x = 0; x < n; x++)
			for (int y = 0; y < m; y++) // for every value of the buffers
			{
				ftData[x][y].setRe(ftData[x][y].getRe() / d);
				ftData[x][y].setIm(ftData[x][y].getIm() / d);
			}


		return ftData;

	}

	public static Complex[][] FFT2D(int n, int m, boolean inverse, double[][] dataRe) {

		Complex[][] ftData = new Complex[n][m];

		int l2n = 0, p = 1; // l2n will become log_2(n)
		while (p < n) {
			p *= 2;
			l2n++;
		}
		int l2m = 0;
		p = 1; // l2m will become log_2(m)
		while (p < m) {
			p *= 2;
			l2m++;
		}

		m = 1 << l2m;
		n = 1 << l2n; // Make sure m and n will be powers of 2, otherwise you'll
						// get in an infinite loop

		// Erase all history of this array
		for (int x = 0; x < m; x++)
			// for each column
			for (int y = 0; y < m; y++) // for each row
			{

				ftData[x][y] = new Complex(dataRe[x][y], 0);
				
				

			}

		// Bit reversal of each row

		for (int y = 0; y < m; y++) // for each row
		{
			int j = 0;
			for (int i = 0; i < n - 1; i++) {
				
				ftData[i][y] = new Complex(dataRe[j][y], 0);

				int k = n / 2;
				while (k <= j) {
					j -= k;
					k /= 2;
				}
				j += k;
			}
		}
		// Bit reversal of each column
		double tx = 0, ty = 0;
		for (int x = 0; x < n; x++) // for each column
		{
			int j = 0;
			for (int i = 0; i < m - 1; i++) {
				if (i < j) {
					tx = ftData[x][i].getRe();
					ty = ftData[x][i].getIm();
					ftData[x][i].setRe(ftData[x][j].getRe());
					ftData[x][i].setIm(ftData[x][j].getIm());
					ftData[x][j].setRe(tx);
					ftData[x][j].setIm(ty);
				}
				int k = m / 2;
				while (k <= j) {
					j -= k;
					k /= 2;
				}
				j += k;
			}
		}

		// Calculate the FFT of the columns
		for (int x = 0; x < n; x++) // for each column
		{
			// This is the 1D FFT:
			double ca = -1.0;
			double sa = 0.0;
			int l1 = 1, l2 = 1;
			for (int l = 0; l < l2n; l++) {
				l1 = l2;
				l2 *= 2;
				double u1 = 1.0;
				double u2 = 0.0;
				for (int j = 0; j < l1; j++) {
					for (int i = j; i < n; i += l2) {
						int i1 = i + l1;
						double t1 = u1 * ftData[x][i1].getRe() - u2 * ftData[x][i1].getIm();
						double t2 = u1 * ftData[x][i1].getIm() + u2 * ftData[x][i1].getRe();
						ftData[x][i1].setRe(ftData[x][i].getRe() - t1);
						ftData[x][i1].setIm(ftData[x][i].getIm() - t2);
						ftData[x][i].setRe(ftData[x][i].getRe() + t1);
						ftData[x][i].setIm(ftData[x][i].getIm() + t2);
					}
					double z = u1 * ca - u2 * sa;
					u2 = u1 * sa + u2 * ca;
					u1 = z;
				}
				sa = Math.sqrt((1.0 - ca) / 2.0);
				if (!inverse)
					sa = -sa;
				ca = Math.sqrt((1.0 + ca) / 2.0);
			}
		}
		// Calculate the FFT of the rows
		for (int y = 0; y < m; y++) // for each row
		{
			// This is the 1D FFT:
			double ca = -1.0;
			double sa = 0.0;
			int l1 = 1, l2 = 1;
			for (int l = 0; l < l2m; l++) {
				l1 = l2;
				l2 *= 2;
				double u1 = 1.0;
				double u2 = 0.0;
				for (int j = 0; j < l1; j++) {
					for (int i = j; i < n; i += l2) {
						int i1 = i + l1;
						double t1 = u1 * ftData[i1][y].getRe() - u2 * ftData[i1][y].getIm();
						double t2 = u1 * ftData[i1][y].getIm() + u2 * ftData[i1][y].getRe();
						ftData[i1][y].setRe(ftData[i][y].getRe() - t1);
						ftData[i1][y].setIm(ftData[i][y].getIm() - t2);
						ftData[i][y].setRe(ftData[i][y].getRe() + t1);
						ftData[i][y].setIm(ftData[i][y].getIm() + t2);
					}
					double z = u1 * ca - u2 * sa;
					u2 = u1 * sa + u2 * ca;
					u1 = z;
				}
				sa = Math.sqrt((1.0 - ca) / 2.0);
				if (!inverse)
					sa = -sa;
				ca = Math.sqrt((1.0 + ca) / 2.0);
			}
		}

		int d;
		if (inverse)
			d = n;
		else
			d = m;
		for (int x = 0; x < n; x++)
			for (int y = 0; y < m; y++) // for every value of the buffers
			{
				ftData[x][y].setRe(ftData[x][y].getRe() / d);
				ftData[x][y].setIm(ftData[x][y].getIm() / d);
			}


		return ftData;

	}

}
