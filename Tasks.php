<?php

/*start of first snippet*/
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
echo "$a, $b\n";
/*end of first snippet*/

/*start of second snippet*/
class Father
{
    const NAME = 'Father';

    public function getName(): string
    {
        return self::NAME;
    }

    public function getStaticName(): string
    {
        return static::NAME;
    }
}

class Son extends Father
{
    const NAME = 'Son';
}

$b = new Son;

echo $b->getName() . "\n";
echo $b->getStaticName() . "\n";
/*end of second snippet*/

/*start of third snippet*/
$data = array(1,2,3,4);
foreach ($data as &$entry) {

}

print_r($data);

$test = array(10,20,30,40);
foreach ($test AS $entry) {

}

print_r($data);
/*end of third snippet*/