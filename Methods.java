package com.company;

public class Methods {
    private int numPoints;
    private float gamma, w0, w, f0, y0, v0, dt;

    public Methods(int numPoints, float gamma, float w0, float w, float f0, float y0, float v0, float dt) {
        this.numPoints = numPoints;
        this.gamma     = gamma;
        this.w0        = w0;
        this.w         = w;
        this.f0        = f0;
        this.y0        = y0;
        this.v0        = v0;
        this.dt        = dt;
    }

    public void setNumPoints(int numPoints) { this.numPoints = numPoints; }
    public void setGamma    (float gamma)   { this.gamma     = gamma;     }
    public void setW0       (float w0)      { this.w0        = w0;        }
    public void setW        (float w)       { this.w         = w;         }
    public void setF0       (float f0)      { this.f0        = f0;        }
    public void setY0       (float y0)      { this.y0        = y0;        }
    public void setV0       (float v0)      { this.v0        = v0;        }
    public void setDt       (float dt)      { this.dt        = dt;        }

    public int   getNumPoints() { return numPoints; }
    public float getGamma    () { return gamma;     }
    public float getW0       () { return w0;        }
    public float getW        () { return w;         }
    public float getF0       () { return f0;        }
    public float getY0       () { return y0;        }
    public float getV0       () { return v0;        }
    public float getDt       () { return dt;        }

    public class Solution {
        private final float[] yArr, vArr;
        private final float   minY, maxY, minV, maxV;
        private final int     numPoints;

        private Solution(float[] yArr, float[] vArr, float minY, float maxY, float minV, float maxV) {
            this.yArr      = yArr;
            this.vArr      = vArr;
            this.minY      = minY;
            this.maxY      = maxY;
            this.minV      = minV;
            this.maxV      = maxV;
            this.numPoints = Methods.this.numPoints;
        }

        public float[] getYArray   () { return yArr;      }
        public float[] getVArray   () { return vArr;      }
        public float   getMinY     () { return minY;      }
        public float   getMaxY     () { return maxY;      }
        public float   getMinV     () { return minV;      }
        public float   getMaxV     () { return maxV;      }
        public int     getNumPoints() { return numPoints; }
    }

    private float diffFunction(float y, float v, float t) {
        return f0 * (float)Math.cos(w * t) -2 * gamma * v - w0 * w0 * (float)Math.sin(y);
    }

    public Solution eilersMethod() {
        float[] yArr = new float[numPoints];
        float[] vArr = new float[numPoints];

        float y, v, a;
        float minY, maxY, minV, maxV;

        yArr[0] = y = y0;
        vArr[0] = v = v0;
        a = diffFunction(y, v, 0);

        minY = maxY = y;
        minV = maxV = v;

        for (int i = 1; i != numPoints; i++) {
            yArr[i] = y += v * dt;
            vArr[i] = v += a * dt;
            a = diffFunction(y, v, numPoints * dt);

            if (y < minY) minY = y;
            else if (y > maxY) maxY = y;

            if (v < minV) minV = v;
            else if (v > maxV) maxV = v;
        }

        return new Solution(yArr, vArr, minY, maxY, minV, maxV);
    }
}
