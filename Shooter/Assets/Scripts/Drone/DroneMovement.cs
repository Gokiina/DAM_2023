using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DroneMovement : MonoBehaviour
{
    public float speed = 2f;
    public bool startMoving = false;

    // Update is called once per frame
    void Update()
    {
        if (startMoving)
        {
            moveForward();
        }
            
    }

    void moveForward()
    {
        transform.Translate(new Vector3(0, 0, speed * Time.deltaTime));
    }
}
