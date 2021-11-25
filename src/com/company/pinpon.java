package com.company;
/*
программа выводит ping pong каждую секунду
 */
public class pinpon
{
    public static void main(String[] args)//основная функция
    {
        Object lock = new Object();// Объявление объекта lock, который будет необходим для подачи доступа в метод потокам.
        Thread ping = new Thread(new PingPongThread(lock, "Ping"));//Объявление потока "ping"
        Thread pong = new Thread(new PingPongThread(lock, "Pong"));//Объявление потока "pong"
        ping.start();// Запуск "ping".
        pong.start();// Запуск "pong".
    }
}


class PingPongThread implements Runnable//наследование интерфейса
{
    private Object lock;//поле lock.
    private String name;//поле name.

    public PingPongThread(Object lock, String name)//Конструктор
    {
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run()//Метод, который запустится после запуска потока методом start().
    {
        synchronized (lock)//Данный блок может выполняться только одним потоком одновременно.
        {
            while(true)
            {
                System.out.println(name);//Вывод текущего имени ("ping" или "pong").
                try// При отсутствии исключений:
                {
                    Thread.sleep(1000);//Приостановка потока на 1 секунду
                }
                catch (InterruptedException e)//В случае исключения:
                {
                    e.printStackTrace();//Указание строки, которая вызвала исключение
                }
                lock.notify();//Продолжает работу потока
                try//Если нет исключений, то выполнить:
                {
                    lock.wait(1000);//Установка потока в режим ожидания, пока другой потом не вызовет метод notify()
                }
                catch (InterruptedException e)//В случае исключения:
                {
                    e.printStackTrace();//Указание строки, которая вызвала исключение
                }
            }
        }
    }
}