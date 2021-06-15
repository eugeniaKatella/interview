<?php


//first task
$a = 2;
$b = 3;
function t() {
    global $b;
    static $a;
    $a++;
    $b += 2;
}
t();
t();
t();
echo "$a, $b";





//second task
class Father
{
    const NAME = 'Father';

    public function getName()
    {
        return self::NAME;
    }

    public function getStaticName()
    {
        return static::NAME;
    }
}

class Son extends Father
{
    const NAME = 'Son';
}
$b = new Son;

echo $b->getName();
echo $b->getStaticName();

