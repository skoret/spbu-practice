#!/usr/bin/python3

import sys
from argparse import ArgumentParser

class grammar():

    rules = []
    max_length = 0
    count_operations = 0

    def __init__(self, verbose=False, filename=''):
        self.verbose = verbose
        self.filename = filename
        self.template = '{:>8} -> {:<12} | {:>%d} -> {:<%d}\n'
        if self.verbose:
            open(self.filename, 'w').close()
        self.read_rules()
    
    def read_rules(self):
        self.rules = []
        with open(self.__class__.__name__ + '.txt', 'r') as f_in:
            for line in f_in:
                line = line[:-1]
                if line == '':
                    continue
                else:
                    head, _, body = line.split(' ')
                    self.rules.append((head, body))

    def parse(self, number):
        pass

    def apply_rule(self, rule, string):
        new = string.replace(rule[0], rule[1], 1)
        
        if new != string:
            self.count_operations += 1
            if self.verbose:
                with open(self.filename, 'a') as f_out:
                    f_out.write((self.template % (self.max_length, self.max_length)).format(rule[0], rule[1], string, new))
            string = new

        return string

class grammar0(grammar):

    def parse(self, number):
        self.max_length = number + number // 2 + 9
        string = '[init]'
        self.count_operations = 0

        if (number == 2):
            string = self.apply_rule(self.rules[0], string)
        else:
            string = self.apply_rule(self.rules[1], string)
            
            gen1 = True
            while (self.count_operations < number - 2):
                if (gen1):
                    string = self.apply_rule(self.rules[-2], string)
                    gen1 = False
                else:
                    string = self.apply_rule(self.rules[-1], string)
                    gen1 = True
            
            if (gen1):
                string = self.apply_rule(self.rules[2], string)
            else:
                string = self.apply_rule(self.rules[3], string)

            done = False
            while not done:
                done = True

                for rule in self.rules:
                    new = self.apply_rule(rule, string)

                    if (new != string):
                        done = False
                        string = new
                        break
        
        return all(symbol == "0" for symbol in string)

class grammar1(grammar):

    def parse(self, number):
        self.count_operations = 0
        self.max_length = 7 + number
        string = '[init]'
        
        if (number == 2):
            string = self.apply_rule(self.rules[0], string)
        else:
            string = self.apply_rule(self.rules[1], string)

            while (self.count_operations < number - 1):
                string = self.apply_rule(self.rules[-1], string)
            done = False
            while not done:
                done = True

                for rule in self.rules:
                    new = self.apply_rule(rule, string)
                    if (new != string):
                        done = False
                        string = new
                        break
    
        return all(symbol == '0' for symbol in string)

class grammar1_1(grammar):

    def parse(self, number):
        self.count_operations = 0
        self.max_length = 2 + number * 2
        string  = '[init]'

        string = self.apply_rule(self.rules[0], string)
        while (self.count_operations < number - 1):
            string = self.apply_rule(self.rules[-1], string)
        done = False
        while not done:
            done = True

            for rule in self.rules:
                    new = self.apply_rule(rule, string)
                    if (new != string):
                        done = False
                        string = new
                        break
    
        return all(symbol == '0' for symbol in string[1:-2])

grammars = {
    0 : grammar0,
    1 : grammar1,
    11: grammar1_1
}

def main():
    parser = ArgumentParser(prog = 'prime_grammar.py', description = 'Free (32 rules) and context sensitive (33 rules) grammars for prime numbers.\nParsing and derive.')
    parser.add_argument('-n', '--number', type = int, help = 'number to check for prime', default = -1)
    parser.add_argument('-t', '--type', type = int, help = 'grammar\'s type: 0 -- free; 1 -- context sensitive', default = 1)
    parser.add_argument('-o', '--output', type=str, help='file for output', default='')
    parser.add_argument('-v', '--verbose', help = 'verbose derive', action='store_true')
    
    args = parser.parse_args()

    g = grammars[args.type](args.verbose, args.output)

    if (args.number > 1):
        if (g.parse(args.number)):
            answer = 'is prime'
        else:
            answer = 'is not prime'
        print('{} {} | count of operations: {}'.format(args.number, answer, g.count_operations))
    else:
        number = 2
        while True:
            if (g.parse(number)):
                print('{} | count of operations: {}'.format(number, g.count_operations))
            number += 1

            # if (number == 10):
            #     break

if (__name__ == "__main__"):
    main()