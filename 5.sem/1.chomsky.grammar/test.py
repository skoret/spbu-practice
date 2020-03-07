from argparse import ArgumentParser

from prime_grammar import grammar0, grammar1, grammar

prime = []

def erat(maxn):
    global prime
    prime = [True for _ in range(maxn + 1)]
    prime[0] = prime[1] = False
    for i in range(2, maxn + 1):
        if prime[i]:
            for j in range(i * i, maxn + 1, i):
                prime[j] = False

def test(g: grammar, maxn: int):
    print('----- Test {} -----'.format(g.__class__.__name__))
    g_result = [g.parse(i) for i in range(2, maxn + 1)]
    check = [g_res == cor for g_res, cor in zip(g_result, prime[2:maxn+1])]
    map(lambda num, res:\
        print('wa for {} ({})'.format(num + 2, prime[num + 2]) if res else None), check)
    return all(check)


if __name__ == '__main__':
    parser = ArgumentParser(prog='test.py', description='Test for prime_number.py')
    parser.add_argument('-t0', '--type0', action='store_true', help='test grammar 0')
    parser.add_argument('-t1', '--type1', action='store_true', help='test grammar 1')
    parser.add_argument('-n', '--maxn', type=int, default=100, help='max number for test')

    args = parser.parse_args()

    erat(args.maxn)

    if args.type0:
        g = grammar0()
        if test(g, args.maxn):
            print('correct')

    if args.type1:
        g = grammar1()
        if test(g, args.maxn):
            print('correct')
