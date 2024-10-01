using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Shot : MonoBehaviour
{
    public Transform shotInitPoint;
    public GameObject bullet;
    public float shotForce = 1500f;

    void Update()
    {
        if (Input.GetMouseButtonDown(0) && GameManager.Instance.ammo>0)
        {
            Debug.Log(GameManager.Instance.ammo);
            GameManager.Instance.ammo--;
            GameObject newBullet = Instantiate(bullet, shotInitPoint.position, shotInitPoint.rotation);
            newBullet.GetComponent<Rigidbody>().AddForce(shotInitPoint.forward * shotForce);
            Destroy(newBullet, 5);

            GameManager.Instance.textAmmo.text = GameManager.Instance.ammo.ToString();
        }
        
    }
}
