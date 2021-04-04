<?php

/**
 * Created by PhpStorm.
 * User: alexanderjeitlerstehr
 * Date: 27.05.17
 * Time: 07:12
 */
class Column
{
    private $name;
    private $shownName;
    private $type;
    private $other;

    public function __construct($name, $shownName, $type, $other)
    {
        $this->name = $name;
        $this->shownName = $shownName;
        $this->type = $type;
        $this->other = $other;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getShownName()
    {
        return $this->shownName;
    }

    public function getType()
    {
        return $this->type;
    }

    public function getOther()
    {
        return $this->other;
    }

    public function toString()
    {
        $returnValue = '';

        if($this->other != null)
        {
            $returnValue = $this->name . ' ' . $this->type . ', ' . $this->other;
        }
        else
        {
            $returnValue = $this->name . ' ' . $this->type;
        }

        return $returnValue;
    }
}