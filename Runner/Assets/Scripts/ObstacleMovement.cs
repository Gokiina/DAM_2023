using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObstacleMovement : MonoBehaviour
{
    //public float speed = 1;
    public bool isStopped = false;

    // Update is called once per frame
    void Update()
    {
        //transform.position += Vector3.left * speed * Time.deltaTime;

        if (Time.timeScale != 0)
        {
            GetComponent<Transform>().Translate(-0.001f, 0, 0);
        }
    }
}
