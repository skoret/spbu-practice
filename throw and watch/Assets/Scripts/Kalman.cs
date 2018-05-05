// Copyright (c) 2018 by Sergey Skaredov
// Licensed under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

using Emgu.CV;
using Emgu.CV.CvEnum;
using Emgu.CV.Structure;
using UnityEngine;

namespace Kalman
{ 
    public class KalmanFilter
    {
        private Matrix<float> E;

        public Matrix<float> X0 { get; private set; } 
        public Matrix<float> P0 { get; private set; }

        public Matrix<float> F { get; private set; }
        public Matrix<float> B { get; private set; }
        public Matrix<float> U { get; private set; }
        public Matrix<float> Q { get; private set; }
        public Matrix<float> H { get; private set; }
        public Matrix<float> R { get; private set; }

        public Matrix<float> State { get; private set; } 
        public Matrix<float> Covariance { get; private set; }

        public KalmanFilter(Matrix<float> f, Matrix<float> b, Matrix<float> u, Matrix<float> q, Matrix<float> h, Matrix<float> r)
        {
            E = new Matrix<float>(f.Rows, f.Rows);
            E.SetIdentity();

            F = f;
            B = b;
            U = u;
            Q = q;
            H = h;
            R = r;
        }

        public void SetState(Matrix<float> state, Matrix<float> covariance)
        {
            State = state;
            Covariance = covariance;
        }

        public KalmanFilter(float q = 1.0e-2f, float r = 1.0e-2f)
        {
            E = new Matrix<float>(9, 9);
            E.SetIdentity();

            F = new Matrix<float>(new float[,] {
                                                    { 1, 0, 0, 1, 0, 0, 0.5f,    0,    0 },
                                                    { 0, 1, 0, 0, 1, 0,    0, 0.5f,    0 },
                                                    { 0, 0, 1, 0, 0, 1,    0,    0, 0.5f },
                                                    { 0, 0, 0, 1, 0, 0,    1,    0,    0 },
                                                    { 0, 0, 0, 0, 1, 0,    0,    1,    0 },
                                                    { 0, 0, 0, 0, 0, 1,    0,    0,    1 },
                                                    { 0, 0, 0, 0, 0, 0,    1,    0,    0 },
                                                    { 0, 0, 0, 0, 0, 0,    0,    1,    0 },
                                                    { 0, 0, 0, 0, 0, 0,    0,    0,    1 },
                                               });

            H = new Matrix<float>(new float[,] {
                                                    { 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                                                    { 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                                                    { 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                                               });

            Q = new Matrix<float>(9, 9);
            Q.SetIdentity(new MCvScalar(q));

            R = new Matrix<float>(3, 3);
            R.SetIdentity(new MCvScalar(r));


            State = new Matrix<float>(new float[,] {
                                                        { 0 }, { 0 }, { 0 },
                                                        { 0 }, { 0 }, { 0 },
                                                        { 0 }, { 0 }, { 0 }
                                                   });

            Covariance = new Matrix<float>(9, 9);
            Q.SetIdentity(new MCvScalar(10.0));
        }

        public Vector3 GetPose()
        {
            return new Vector3(State[0, 0], State[1, 0], State[2, 0]);
        }

        public Vector3 GetVel()
        {
            return new Vector3(State[3, 0], State[4, 0], State[5, 0]);
        }

        public Vector3 GetAcc()
        {
            return new Vector3(State[6, 0], State[7, 0], State[8, 0]);
        }

        public Vector3 PoseEstimate(Vector3 acceleration, float td)
        {
            float td2 = 0.5f * td * td;
            F = new Matrix<float>(new float[,] {
                                                    { 1, 0, 0, td,  0,  0, td2,   0,   0 },
                                                    { 0, 1, 0,  0, td,  0,   0, td2,   0 },
                                                    { 0, 0, 1,  0,  0, td,   0,   0, td2 },
                                                    { 0, 0, 0,  1,  0,  0,  td,   0,   0 },
                                                    { 0, 0, 0,  0,  1,  0,   0,  td,   0 },
                                                    { 0, 0, 0,  0,  0,  1,   0,   0,  td },
                                                    { 0, 0, 0,  0,  0,  0,   1,   0,   0 },
                                                    { 0, 0, 0,  0,  0,  0,   0,   1,   0 },
                                                    { 0, 0, 0,  0,  0,  0,   0,   0,   1 },
                                               });

            Matrix<float> z = new Matrix<float>(3, 1);

            Debug.Log(State.Size);

            z[0, 0] = State[0, 0] + State[3, 0] * td + acceleration.x * td2;
            z[1, 0] = State[1, 0] + State[4, 0] * td + acceleration.z * td2;
            z[2, 0] = State[2, 0] + State[5, 0] * td + acceleration.y * td2;

            Correct(z);

            return GetPose();
        }

        private void Correct(Matrix<float> z)
        {
            X0 = F * State; // + (B * U);
            P0 = F * Covariance * F.Transpose() + Q;

            Matrix<float> hphr = new Matrix<float>(R.Rows, R.Cols);
            hphr.SetIdentity();
            CvInvoke.Invert((H * P0 * H.Transpose() + R), hphr, DecompMethod.Svd);

            var k = P0 * H.Transpose() * hphr;
            State = X0 + (k * (z - (H * X0)));
            Covariance = (E - k * H) * P0;
        }
    }
}